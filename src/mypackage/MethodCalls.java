package mypackage;

public class MethodCalls {
		public Method Caller; 
		public Method Callee;
		public MethodCalls(Method caller, Method callee) {
			super();
			Caller = caller;
			Callee = callee;
		}
		
		public MethodCalls() {
			// TODO Auto-generated constructor stub
		}
		public Method getCaller() {
			return Caller;
		}
		public void setCaller(Method caller) {
			Caller = caller;
		}
		public Method getCallee() {
			return Callee;
		}
		public void setCallee(Method callee) {
			Callee = callee;
		} 
		
		
		
}
