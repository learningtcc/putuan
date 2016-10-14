package com.advanpro.putuan.utils.common;

/**
 * @author Retina.Ye
 * @since 2014/9/22
 */
public class BaseQuery {
    static int DEFAULT_PAGE_SIZE = 15;

    static int DEFAULT_PAGE_NO = 1;

    //是否导出
    private boolean isExprot = false;

    /**
     * 第几页
     */
    protected int pageNo = DEFAULT_PAGE_NO;

    /**
     * 每页显示对象个数
     */
    protected int pageSize = DEFAULT_PAGE_SIZE;

    // 和上面方法一样
    public int getStart() {
        return (pageNo - 1) * pageSize;
    }

    /**
     * @return the pageNo
     */
    public int getPageNo() {
        return pageNo;
    }

    /**
     * @param pageNo the pageNo to set
     */
    public void setPageNo(int pageNo) {
        if (pageNo > 0) {
            this.pageNo = pageNo;
        }
        else {
            this.pageNo = DEFAULT_PAGE_NO;
        }
    }

    /**
     * @return the pageSize
     */
    public int getPageSize() {
        return pageSize;
    }

    /**
     * @param pageSize the pageSize to set
     */
    public void setPageSize(int pageSize) {
        if (pageSize > 0) {
            this.pageSize = pageSize;
        }
        else {
            this.pageSize = DEFAULT_PAGE_SIZE;
        }
    }

    public boolean isExprot() {
        return isExprot;
    }

    public void setExprot(boolean isExprot) {
        this.isExprot = isExprot;
    }

    public String getPageSql(String sql) {
        String pageSql = "SELECT * FROM (SELECT rownum num, s.* FROM ( ";
        pageSql += sql;
        pageSql += " ) s WHERE rownum <= ?) WHERE num >= ?";
        return pageSql;
    }

    public static void main(String[] args) {
        BaseQuery bq = new BaseQuery();
        bq.setPageNo(2);
        bq.setPageSize(20);
        System.out.println(bq.getStart());
        System.out.println(bq.getPageSize());
    }
}
