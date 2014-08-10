package com.github.kd;

public enum NetworkProperties {
	PARALLELISMDEGREE {
		@Override
		public String getDefaultValue() {
			// TODO Auto-generated method stub
			return "3";
		}
	},
	KEYSIZE {
		@Override
		public String getDefaultValue() {
			// TODO Auto-generated method stub
			return "160";
		}
	},
	CONTACTSINTHEBUCKET {
		@Override
		public String getDefaultValue() {
			// TODO Auto-generated method stub
			return "20";
		}
	},
	TEXPIRE {
		@Override
		public String getDefaultValue() {
			// TODO Auto-generated method stub
			return "86400";
		}
	},
	TREFRESH {
		@Override
		public String getDefaultValue() {
			// TODO Auto-generated method stub
			return "3600";
		}
	},
	TREPLICATE {
		@Override
		public String getDefaultValue() {
			// TODO Auto-generated method stub
			return "3600";
		}
	},
	TREPUBLISH {
		@Override
		public String getDefaultValue() {
			// TODO Auto-generated method stub
			return "86400";
		}
	};

	public abstract String getDefaultValue();

}
