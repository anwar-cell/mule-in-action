package com.prancingdonkey.service;


import java.util.List;

import org.mule.api.MuleEventContext;
import org.mule.api.lifecycle.Callable;

import com.prancingdonkey.model.Brew;

public class BrewServiceImpl implements BrewService, Callable {

    public List<Brew> getBrews() {
        return Brew.findAll();
    }

	@Override
	public Object onCall(MuleEventContext eventContext) throws Exception {
		return getBrews();
	}
}

