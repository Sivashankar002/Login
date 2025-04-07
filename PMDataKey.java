package com.FCAPS.model;

import org.springframework.data.cassandra.core.mapping.PrimaryKeyClass;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import static org.springframework.data.cassandra.core.cql.PrimaryKeyType.PARTITIONED;
import static org.springframework.data.cassandra.core.cql.PrimaryKeyType.CLUSTERED;

@PrimaryKeyClass
public class PMDataKey {

	@PrimaryKeyColumn(name ="neid", type = PARTITIONED)
	private int neid;
	
	@PrimaryKeyColumn(name ="timestamp", type = CLUSTERED)
	private String timestamp;
		
	@PrimaryKeyColumn(name ="portname", type = CLUSTERED)
    private String portname;


	public int getNeid(){ return neid;}
	public void setNeid(int neid) {this.neid=neid; }
	
	public String getTimestamp(){ return timestamp;}
	public void setTimestamp(String timestamp) {this.timestamp=timestamp; }
	
	public String getPortname(){ return portname;}
	public void setPortname(String portname) {this.portname=portname;}
}
//	@Override
//	public boolean equals(Object o) {
//		if(this == o) return true;
//		if(o == null || getClass()!= o.getClass()) return false;
//		PMDataKey pmKey = (PMDataKey) o;
//		return Objects.equals(timestamp,  pmKey.timestamp) &&
//				Objects.equals(neid,  pmKey.neid) &&
//				Objects.equals(port,  pmKey.port);	
//	}
//	@Override
//	public int hashCode() { return Objects.hash(timestamp, neid, port); }
	
//public PMDataKey() {}
//public PMDataKey(String neid, String timestamp, String portName) {
//	this.neid = neid;
//	this.timestamp=timestamp;
//	this.portName=portName;
//}

