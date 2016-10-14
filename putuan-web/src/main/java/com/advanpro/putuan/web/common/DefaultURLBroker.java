package com.advanpro.putuan.web.common;

public class DefaultURLBroker extends URLBroker {

	public DefaultURLBroker(URLBroker urlBroker) {
		super(urlBroker);
	}

	public DefaultURLBroker() {

	}

	@Override
	public URLBroker newInstance() {
		return new DefaultURLBroker(this);
	}

}
