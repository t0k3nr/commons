package org.uche.t0ken.api.bitstamp;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

import org.uche.t0ken.commons.enums.StatGranularity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
public class OhlcData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/*{
		  "data": {
		    "pair": "BTC/USD",
		    "ohlc": [
		      {
		        "timestamp": "1313698080",
		        "open": "10.9",
		        "high": "10.9",
		        "low": "10.9",
		        "close": "10.9",
		        "volume": "0.00000000"
		      }
		    ]
		  }
		}*/
	
	private Data data;
	
	public Data getData() {
		return data;
	}

	public void setData(Data data) {
		this.data = data;
	}

	@JsonIgnoreProperties(ignoreUnknown = true)
	@JsonInclude(Include.NON_NULL)
	public static class Data {
		
		private String pair;
		private List<Ohlc> ohlc;
		public String getPair() {
			return pair;
		}
		public void setPair(String pair) {
			this.pair = pair;
		}
		
		
		@JsonIgnoreProperties(ignoreUnknown = true)
		@JsonInclude(Include.NON_NULL)
		public static class Ohlc {
			private String timestamp;
			private String open;
			private String high;
			private String low;
			private String close;
			private String volume;
			public String getTimestamp() {
				return timestamp;
			}
			public void setTimestamp(String timestamp) {
				this.timestamp = timestamp;
			}
			public String getOpen() {
				return open;
			}
			public void setOpen(String open) {
				this.open = open;
			}
			public String getHigh() {
				return high;
			}
			public void setHigh(String high) {
				this.high = high;
			}
			public String getLow() {
				return low;
			}
			public void setLow(String low) {
				this.low = low;
			}
			public String getClose() {
				return close;
			}
			public void setClose(String close) {
				this.close = close;
			}
			public String getVolume() {
				return volume;
			}
			public void setVolume(String volume) {
				this.volume = volume;
			}
			
		}


		public List<Ohlc> getOhlc() {
			return ohlc;
		}
		public void setOhlc(List<Ohlc> ohlc) {
			this.ohlc = ohlc;
		}
	}
	
	
	
	
	

}
