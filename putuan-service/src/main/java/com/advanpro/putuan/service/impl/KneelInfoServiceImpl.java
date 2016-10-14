package com.advanpro.putuan.service.impl;

import com.advanpro.putuan.dao.KneelInfoDao;
import com.advanpro.putuan.dao.UserDao;
import com.advanpro.putuan.dao.query.UserKneelQuery;
import com.advanpro.putuan.dao.query.UserQuery;
import com.advanpro.putuan.model.*;
import com.advanpro.putuan.service.KneelInfoService;
import com.advanpro.putuan.utils.common.Page;
import com.advanpro.putuan.utils.date.DateUtils;
import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 作者： Vance
 * 时间： 2016/9/5
 * 描述： ${todo}.
 */
@Service
public class KneelInfoServiceImpl implements KneelInfoService {

    @Autowired
    private KneelInfoDao kneelInfoDao;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private UserDao userDao;

    @Override
    public void add(KneelInfo KneelInfo) {
        kneelInfoDao.add(KneelInfo);
    }

    @Override
    public void update(KneelInfo KneelInfo) {
        kneelInfoDao.update(KneelInfo);
    }

    @Override
    public List<KneelInfo> queryByUserId(int userId) {
        return queryByUserIdAndTime(userId, null, null);
    }

    @Override
    public List<KneelInfo> queryByUserIdAndTime(int userId, Date beginTime, Date endTime) {
        return kneelInfoDao.queryByUserIdAndTime(userId, beginTime, endTime);
    }

    @Override
    public List<KneelInfo> queryByTime(Date beginTime, Date endTime) {
        return kneelInfoDao.queryByTime(beginTime, endTime);
    }

    /**
     * 统计一段时间内所有用户的跪拜信息
     *
     * @param beginTime 开始时间
     * @param endTime   结束时间
     * @param filter    统计条件 SEX-性别 默认-省份
     * @return
     */
    @Override
    public List<KneelInfoSummary> getKneelInfoSummary(Date beginTime, Date endTime, String filter) {
        List<KneelInfo> kneelInfoList = kneelInfoDao.queryByTime(beginTime, endTime);
        Map<String, KneelInfoSummary> kneelInfoSummaryMap = getKneelInfoSummary(kneelInfoList, filter, null);

        List<KneelInfoSummary> kneelInfoSummaryList = Lists.newArrayList(kneelInfoSummaryMap.values());

        Collections.sort(kneelInfoSummaryList, new Comparator() {
            public int compare(Object first, Object second) {
                return ((KneelInfoSummary) second).getKneelCount() - ((KneelInfoSummary) first).getKneelCount();
            }

        });

        if (kneelInfoSummaryList.size() <= 5) {
            return kneelInfoSummaryList;
        }

        KneelInfoSummary otherKneelInfoSummary = new KneelInfoSummary("其它", 0);
        List<KneelInfoSummary> kneelInfoSummaryTopList = Lists.newArrayList();
        for (int i = 0; i < kneelInfoSummaryList.size(); i++) {
            if (i < 5) {
                kneelInfoSummaryTopList.add(kneelInfoSummaryList.get(i));
            } else {
                otherKneelInfoSummary.setKneelCount(otherKneelInfoSummary.getKneelCount() + kneelInfoSummaryList.get(i).getKneelCount());
            }
        }
        kneelInfoSummaryTopList.add(otherKneelInfoSummary);


        return kneelInfoSummaryTopList;
    }

    /**
     * 获取一段时间内所有用户的跪拜趋势
     *
     * @param beginTime 开始时间
     * @param endTime   结束时间
     * @param filter    统计条件 SEX-性别 默认-省份
     * @return
     */
    @Override
    public List<KneelInfoTrend> getKneelInfoTrend(Date beginTime, Date endTime, String filter) {
        int day = DateUtils.daysBetween(beginTime, endTime);
        List<KneelInfo> kneelInfoList = kneelInfoDao.queryByTime(beginTime, endTime);

        Map<String, KneelInfoTrend> kneelInfoTrend = Maps.newHashMap();
        if (kneelInfoList.isEmpty()) {
            return Lists.newArrayList();
        }

        Set userIds = Sets.newHashSet();
        for (KneelInfo kneelInfo : kneelInfoList) {
            userIds.add(kneelInfo.getUserId());
        }

        List<User> userList = userDao.queryByIds(Lists.newArrayList(userIds));
        Map<Integer, User> userMap = Maps.uniqueIndex(userList, new Function<User, Integer>() {
            public Integer apply(User user) {
                return user.getId();
            }
        });

        for (User user : userList) {
            String key = user.getProvince();
            if ("SEX".equalsIgnoreCase(filter)) {
                key = String.valueOf(user.getSex());
            }
            if (!kneelInfoTrend.containsKey(key)) {
                List<Integer> kneelCountList = Lists.newArrayList();
                for (int i = 0; i < day; i++) {
                    kneelCountList.add(0);
                }
                kneelInfoTrend.put(key, new KneelInfoTrend(key, kneelCountList));
            }
        }

        for (int i = 0; i < day; i++) {
            final Date curBeginTime = DateUtils.addDays(beginTime, i);
            final Date curEndTime = DateUtils.addDays(beginTime, i + 1);
            List<KneelInfo> kneelInfos = Lists.newArrayList(Collections2.filter(kneelInfoList, new Predicate<KneelInfo>() {
                @Override
                public boolean apply(KneelInfo kneelInfo) {
                    long startTime = kneelInfo.getStartTime().getTime();
                    return startTime - curBeginTime.getTime() >= 0 && curEndTime.getTime() - startTime > 0;
                }
            }));
            for (KneelInfo kneelInfo : kneelInfos) {
                User user = userMap.get(kneelInfo.getUserId());
                if (user != null) {
                    String key = user.getProvince();
                    int kneeCount = kneelInfoTrend.get(key).getKneelCounts().get(i) + kneelInfo.getKneelCount();
                    kneelInfoTrend.get(key).getKneelCounts().set(i, kneeCount);
                }
            }
        }

        List<KneelInfoTrend> kneelInfoTrendList = Lists.newArrayList(kneelInfoTrend.values());
        Collections.sort(kneelInfoTrendList, new Comparator() {
            public int compare(Object first, Object second) {
                List<Integer> firstKneelCounts = ((KneelInfoTrend) first).getKneelCounts();
                List<Integer> secondKneelCounts = ((KneelInfoTrend) second).getKneelCounts();
                int firstSum = 0;
                int secondSum = 0;
                for (Integer count : firstKneelCounts) {
                    firstSum += count;
                }
                for (Integer count : secondKneelCounts) {
                    secondSum += count;
                }
                return secondSum - firstSum;
            }

        });

        if (kneelInfoTrendList.size() > 5) {
            return kneelInfoTrendList.subList(0, 5);
        }

        return kneelInfoTrendList;
    }


    /**
     * 根据条件查询用户跪拜明细
     *
     * @param userKneelQuery 查询条件
     * @return
     */
    @Override
    public Page<KneelInfo> getKneelInfoDetail(UserKneelQuery userKneelQuery) {
        Page<KneelInfo> page = new Page<>(userKneelQuery.getPageNo(), userKneelQuery.getPageSize());
        userKneelQuery.setStart(page.getStart());
        userKneelQuery.setLimit(userKneelQuery.getPageSize());

        Map<Integer, User> userMap = getUserMap(userKneelQuery);
        if (userMap == null || userMap.isEmpty()) {
            return page;
        }

        userKneelQuery.setUserIdList(Lists.newArrayList(userMap.keySet()));
        List<KneelInfo> kneelInfoList = kneelInfoDao.queryByCondition(userKneelQuery);
        for (KneelInfo kneelInfo : kneelInfoList) {
            kneelInfo.setUser(userMap.get(kneelInfo.getUserId()));
        }
        page.setResult(kneelInfoList);
        page.setTotalCount(kneelInfoDao.countByCondition(userKneelQuery));
        return page;
    }

    /**
     * 根据省份和时间筛选用户跪拜排行
     *
     * @param province  省份
     * @param beginTime 开始时间
     * @param endTime   结束时间
     * @return
     */
    @Override
    public List<UserKneelInfo> getKneelInfoRanking(String province, Date beginTime, Date endTime) {
        province = ("ALL".equalsIgnoreCase(province) || "全国".equals(province)) ? null : province;
        List<UserKneelInfo> userKneelInfoList = Lists.newArrayList();
        UserKneelQuery userKneelQuery = new UserKneelQuery();
        userKneelQuery.setProvince(province);
        Map<Integer, User> userMap = getUserMap(userKneelQuery);

        userMap = userService.filterBindWxUser(userMap);

        if (userMap == null || userMap.isEmpty()) {
            return userKneelInfoList;
        }
        userKneelQuery.setUserIdList(Lists.newArrayList(userMap.keySet()));
        userKneelQuery.setStart(-1);
        userKneelQuery.setLimit(-1);
        userKneelQuery.setBeginTime(beginTime);
        userKneelQuery.setEndTime(endTime);
        List<KneelInfo> kneelInfoList = kneelInfoDao.queryByCondition(userKneelQuery);
        Map<Integer, KneelInfo> userIdKneelInfoMap = getUserIdToKneel(kneelInfoList, userMap);
        kneelInfoList = Lists.newArrayList(userIdKneelInfoMap.values());

        //根据次数排序
        Collections.sort(kneelInfoList, new Comparator() {
            public int compare(Object first, Object second) {
                return ((KneelInfo) second).getKneelCount() - ((KneelInfo) first).getKneelCount();
            }

        });

        for (int i = 0; i < kneelInfoList.size(); i++) {
            KneelInfo kneelInfo = kneelInfoList.get(i);
            UserKneelInfo userKneelInfo = new UserKneelInfo(kneelInfo.getUserId(), kneelInfo.getUser().getNickName(), kneelInfo.getUser().getHeadimgUrl(), kneelInfo.getKneelCount(), i + 1, beginTime, endTime);
            userKneelInfoList.add(userKneelInfo);
        }

        if (userKneelInfoList.size() > 7) {
            return userKneelInfoList.subList(0, 7);
        }
        return userKneelInfoList;
    }

    /**
     * 获取用户跪拜排名和跪拜数
     *
     * @param user      当前用户
     * @param province
     * @param beginTime 开始时间
     * @param endTime   结束时间   @return
     */
    @Override
    public UserKneelInfo getUserKneelInfo(User user, String province, Date beginTime, Date endTime) {
        List<UserKneelInfo> userKneelInfoList = getKneelInfoRanking(province, beginTime, endTime);
        final int userId = user.getId();
        UserKneelInfo userKneelInfo = new UserKneelInfo(user.getId(), user.getNickName(), user.getHeadimgUrl(), 0, userKneelInfoList.size() + 1, beginTime, endTime);
        for (UserKneelInfo otherUserKneelInfo : userKneelInfoList) {
            if (otherUserKneelInfo.getUserId() == userId) {
                userKneelInfo = otherUserKneelInfo;
            }
        }
        return userKneelInfo;
    }

    @Override
    @Transactional
    public void addOrUpdate(KneelInfo kneelInfo) {
        Date beginTime = DateUtils.getDateStart(DateUtils.getCurrentDate());
        Date endTime = DateUtils.addDays(beginTime, 1);
        List<KneelInfo> kneelInfoList = kneelInfoDao.queryByUserIdAndDeviceId(kneelInfo.getUserId(), kneelInfo.getDeviceId(), beginTime, endTime);
        if (kneelInfoList == null || kneelInfoList.isEmpty()) {
            kneelInfoDao.add(kneelInfo);
        } else {
            KneelInfo updateKneelInfo = kneelInfoList.get(0);
            if (kneelInfo.getKneelCount() > updateKneelInfo.getKneelCount()) {
                updateKneelInfo.setKneelCount(kneelInfo.getKneelCount());
                updateKneelInfo.setStartTime(kneelInfo.getStartTime());
                kneelInfoDao.update(updateKneelInfo);
            }
        }
    }

    @Override
    @Transactional
    public void addOrUpdateBase(BaseKneelInfo baseKneelInfo) {
        Date beginTime = DateUtils.getDateStart(DateUtils.getCurrentDate());
        Date endTime = DateUtils.addDays(beginTime, 1);
        List<BaseKneelInfo> baseKneelInfoList = kneelInfoDao.queryBase(baseKneelInfo.getUserId(), baseKneelInfo.getDeviceId(), beginTime, endTime);
        if (baseKneelInfoList == null || baseKneelInfoList.isEmpty()) {
            kneelInfoDao.addBase(baseKneelInfo);
        } else {
            BaseKneelInfo updateBaseKneelInfo = baseKneelInfoList.get(0);
            if (baseKneelInfo.getCount() > updateBaseKneelInfo.getCount()) {
                updateBaseKneelInfo.setCount(baseKneelInfo.getCount());
                updateBaseKneelInfo.setTime(baseKneelInfo.getTime());
                kneelInfoDao.updateBase(updateBaseKneelInfo);
            }
        }
    }

    @Override
    @Transactional
    public void addOrUpdateWX(int userId, String deviceId, int kneelCount) {
        BaseKneelInfo baseKneelInfo = new BaseKneelInfo();
        baseKneelInfo.setCount(kneelCount);
        baseKneelInfo.setTime(DateUtils.getCurrentDate());
        baseKneelInfo.setUserId(userId);
        baseKneelInfo.setDeviceId(deviceId);
        addOrUpdateBase(baseKneelInfo);

        KneelInfo kneelInfo = new KneelInfo();
        kneelInfo.setDeviceId(deviceId);
        kneelInfo.setUserId(userId);
        kneelInfo.setStartTime(baseKneelInfo.getTime());
        kneelInfo.setKneelCount(kneelCount);
        addOrUpdate(kneelInfo);
    }

    @Override
    public List<KneelInfo> queryByLastUpdate(int userId, Date lastSyncTime) {
        return kneelInfoDao.queryByLastUpdate(userId, lastSyncTime);
    }

    /**
     * 录入历史数据
     *
     * @param user
     * @param kneelInfo
     */
    @Override
    @Transactional
    public void setHistory(User user, KneelInfo kneelInfo) {
        user.setHistoried(1);
        userDao.updateById(user);
        kneelInfoDao.add(kneelInfo);
    }


    /**
     * 获取跪拜用户数
     */
    @Override
    public int getKneelCount() {
        List<KneelInfo> kneelInfoList = kneelInfoDao.queryByTime(null, null);
        int kneelCount = 0;
        for (KneelInfo kneelInfo : kneelInfoList) {
            kneelCount += kneelInfo.getKneelCount();
        }

        return kneelCount;
    }

    /**
     * 根据用户跪拜信息汇总数据
     *
     * @param kneelInfoList 跪拜信息
     * @param filter        统计条件 SEX-性别 默认-省份
     * @param startTime     开始时间
     * @return
     */
    private Map<String, KneelInfoSummary> getKneelInfoSummary(List<KneelInfo> kneelInfoList, String filter, Date startTime) {
        Map<String, KneelInfoSummary> kneelInfoSummary = Maps.newHashMap();
        Map<Integer, KneelInfo> userIdKneelInfoMap = getUserIdToKneel(kneelInfoList, null);
        if (kneelInfoList.isEmpty()) {
            return kneelInfoSummary;
        }

        List<User> userList = userDao.queryByIds(Lists.newArrayList(userIdKneelInfoMap.keySet()));
        for (User user : userList) {
            String key = user.getProvince();
            if ("AGE".equalsIgnoreCase(filter)) {
                key = String.valueOf(user.getAge());
            }
            if ("SEX".equalsIgnoreCase(filter)) {
                key = String.valueOf(user.getSex());
            }
            if (!kneelInfoSummary.containsKey(key)) {
                kneelInfoSummary.put(key, new KneelInfoSummary(key, userIdKneelInfoMap.get(user.getId()).getKneelCount()));
            } else {
                int kneelCount = kneelInfoSummary.get(key).getKneelCount() + userIdKneelInfoMap.get(user.getId()).getKneelCount();
                kneelInfoSummary.get(key).setKneelCount(kneelCount);
            }
        }
        return kneelInfoSummary;
    }

    /**
     * 根据跪拜信息列表获取用户Id对应的跪拜信息的集合
     *
     * @param kneelInfoList 跪拜信息列表
     * @param userMap       用户Map集合
     * @return
     */
    private Map<Integer, KneelInfo> getUserIdToKneel(List<KneelInfo> kneelInfoList, Map<Integer, User> userMap) {
        Map<Integer, KneelInfo> userIdInfoKneelMap = Maps.newHashMap();
        for (KneelInfo kneelInfo : kneelInfoList) {
            int userId = kneelInfo.getUserId();
            if (!userIdInfoKneelMap.containsKey(userId)) {
                if (userMap != null && !userMap.isEmpty()) {
                    kneelInfo.setUser(userMap.get(userId));
                }
                userIdInfoKneelMap.put(userId, kneelInfo);
            } else {
                int kneelCount = kneelInfo.getKneelCount() + userIdInfoKneelMap.get(userId).getKneelCount();
                userIdInfoKneelMap.get(userId).setKneelCount(kneelCount);
            }
        }

        return userIdInfoKneelMap;
    }

    /**
     * 根据用户跪拜条件获取用户ID的Map集合
     *
     * @param userKneelQuery 用户跪拜条件
     * @return
     */
    private Map<Integer, User> getUserMap(UserKneelQuery userKneelQuery) {
        UserQuery userQuery = new UserQuery();
        userQuery.setAge(userKneelQuery.getAge());
        userQuery.setProvince(userKneelQuery.getProvince());
        userQuery.setNickName(userKneelQuery.getNickName());
        userQuery.setSex(userKneelQuery.getSex());
        userQuery.setStart(-1);
        userQuery.setLimit(-1);
        List<User> userList = userDao.queryUserByCondition(userQuery);

        if (userList == null || userList.isEmpty()) {
            return null;
        }

        Map<Integer, User> userMap = Maps.uniqueIndex(userList, new Function<User, Integer>() {
            public Integer apply(User user) {
                return user.getId();
            }
        });
        return userMap;
    }
}
