package org.mule.examples;

import java.io.IOException;
import java.util.concurrent.CopyOnWriteArrayList;

import org.mule.api.MuleEventContext;
import org.mule.api.lifecycle.Callable;
import org.mule.transport.http.ReleasingInputStream;

public class Aggregator implements Callable{

	@Override
	public Object onCall(MuleEventContext eventContext) throws Exception {
		CopyOnWriteArrayList<ReleasingInputStream> responses = (CopyOnWriteArrayList<ReleasingInputStream>) eventContext.getMessage().getPayload();
		
		double a = Double.parseDouble(readStream(responses.get(0)));
		double b = Double.parseDouble(readStream(responses.get(1)));
		return a + b;
	}

	private String readStream(ReleasingInputStream ris) throws IOException{
		int ch = 0;
		StringBuilder sb = new StringBuilder();
		while ((ch =  ris.read()) != -1){
			sb.append((char)ch);
		}
		return sb.toString();
	}
}
