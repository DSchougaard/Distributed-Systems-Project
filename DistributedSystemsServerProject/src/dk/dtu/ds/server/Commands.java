package dk.dtu.ds.server;


public class Commands{

	public class Client{
		public static final int DEBUG 		= 0b00000000;
		public static final int LOGIN 		= 0b00000001;
		public static final int REGISTER 	= 0b00000010;
	}
	
	public class Server{
		public static final int LOGIN_FAIL 	= 0b00000001;
		public static final int LOGIN_OK 	= 0b00000001;
		
	}
}

