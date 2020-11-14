package io.github.parameters;

public class SessionParameters extends Parameters {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8956275941673168472L;
	
//	private TopSession topSession;

//	public SessionParameters(TopSession topSession) {
//		super();
//		this.setTopSession(topSession);
////		for(String key : topSession.keySet()) {
////			String value = (String) topSession.get(key);
////			this.put(key, value);
////		}
//	}

	public SessionParameters(SessionParameters sessionParameters) {
		super(sessionParameters);
//		this.setTopSession(sessionParameters.getTopSession());
	}

	@Override
	public Object get(Object key) {
		Object retval = super.get(key);
//		if ( retval == null ) {
//			return this.getTopSession().get(key);
//		}
		return retval;
	}

//	public TopSession getTopSession() {
//		return topSession;
//	}
//
//	public void setTopSession(TopSession topSession) {
//		this.topSession = topSession;
//	}

//	@SuppressWarnings("rawtypes")
//	public MMRL getDBRL(Oldb mmap, int lineNumber) throws IOException {
//		MMRL dbrl = new MMRL(mmap, lineNumber);
//		
//		Line line = mmap.getLine(lineNumber);
//		String lineString = line.getLineString();
//		if ( lineString!=null && lineString.length() > 1 && lineString.charAt(0)=='#' ) {
//			char kworn = lineString.charAt(1);
//			if ( '0' <= kworn && kworn <= '9') {
//				// in case of line number
//				int refln = Integer.parseInt(lineString.substring(1));
//
//				LPath refPath = mmap.getLPath(refln);
//				String keyword = refPath.get(0).getLineString().trim();
//				boolean isHyperLink = keyword.equals("#HYPERLINK");
//				if ( isHyperLink ) {
//					String dbname = refPath.get(1).getLineString();
//					String targetlnStr = refPath.get(2).getLineString().substring(1);
//					int targetln = Integer.parseInt(targetlnStr);
//
//					//				long start = System.currentTimeMillis();
//					DisplayMindMap subDOLDB = (DisplayMindMap) DBPool.dbpool._getActivedDB(this.topSession, dbname);
//					//				long end = System.currentTimeMillis();
//					//				System.out.println("OLDB initialization eclapsed " + (end - start));
//					Line targetLine = subDOLDB.getLine(targetln);				
//					dbrl = new MMRL(subDOLDB, targetLine);
//				} else {
//					if ( mmap.isDeleted(refln) ){
//						//					output.append(indent + "  +  Deleted \n" );
//						//					output.append(indent + "  +--------------------\n" );
//					} else {
//						Line refLine = mmap.getLine(refln);
//						dbrl = new MMRL(mmap, refLine);
//					}
//				}
//			} 
//		}
//		
//		return dbrl;
//	}
//	

}
