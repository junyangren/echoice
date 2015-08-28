package org.echoice.modules.web.paper;

import java.util.List;
/**
 * 
 * @desc 
 * @author jun yang
 * @date 2007-1-5
 */
public class PageBean {
	public static final int DEFAULT_PAGE_SIZE = 15;
    public static final int MAX_PAGE_SIZE = 100;
    /**
     * 当前页码
     */
	private int currentPageNo;
    /**
     * 每页的记录数，实际记录数小于或等于它
     */
	private int pageSize;
    /**
     * 当前页第一条数据在数据库中的位置
     */
	private int startIndex;	
	
	private int endIndex;
    /**
     * 总页数
     */
    private int totalPageCount;
    /**
     * 总记录数
     */
	private int totalSize;
    /**
     * 当前页中存放的记录
     */
	private List dataList;
	/**
	 * 
	 * @param reqpageNo 当前页码
	 * @param reqPageSize 每页记录数
	 * @param sqlTotalSize　总记录数
	 */
	public PageBean(int reqpageNo,int reqPageSize,int sqlTotalSize) {
		// TODO Auto-generated constructor stub
		this.totalSize=sqlTotalSize;
		//修正每页记录数
		if(reqPageSize<=0){
			this.pageSize=DEFAULT_PAGE_SIZE;
		}else{
			this.pageSize=reqPageSize;
		}
		//得到总页数
		this.totalPageCount = (sqlTotalSize + pageSize - 1) / pageSize;
		//得到当前页
        if (reqpageNo < 1){
        	currentPageNo = 1;
        }else if(reqpageNo>totalPageCount){
        	currentPageNo=totalPageCount;
        }else{
        	currentPageNo=reqpageNo;
        }
        
        //得到每页在数据库中的开始记录
        this.startIndex = (currentPageNo - 1) * pageSize + 1;
        if (startIndex < 1){
        	startIndex = 1;
        }
        
        this.endIndex=currentPageNo*pageSize;
        if(endIndex>totalSize){
        	endIndex=totalSize;
        }
	}
	/**
	 * 判断是否从数据库中取页记录
	 * @return
	 */
	public boolean isGetPageRecord(){
		return getTotalSize()>0;
	}
	
    /**
     * 是否有下一页
     */
    public boolean hasNextPage() {
        return (getCurrentPageNo() < getTotalPageCount());
    }

    /**
     * 是否有上一页
     */
    public boolean hasPreviousPage() {
        return (getCurrentPageNo() > 1);
    }
    /**
     * 得到下一页
     * @return
     */
    public int getNextPage(){
    	return hasNextPage()?getCurrentPageNo()+1:getCurrentPageNo();
    }
    /**
     * 得到上一页
     * @return
     */
    public int getPreviousPage(){
    	return hasPreviousPage()?getCurrentPageNo()-1:1;
    }
    
	public int getStartIndex() {	
		return startIndex;
	}
	
	public int getEndIndex() {	
		return endIndex;
	}
	
	public int getCurrentPageNo() {
		return currentPageNo;
	}

	public List getDataList() {
		return dataList;
	}

	public void setDataList(List dataList) {
		this.dataList = dataList;
	}

	public int getPageSize() {
		return pageSize;
	}

	public int getTotalPageCount() {
		return totalPageCount;
	}
	
	public int getTotalSize() {
		return totalSize;
	}
	
}
