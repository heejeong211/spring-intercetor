package edu.bit.board.page;

import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

public class PageMaker { //페이지 처리하는 거 최종적인 클래스
	
	//한개의 변수의 담아 넣을 변수들은 따로 빼놔야 한다. 이거 대신 그냥 Criteria 클래스 내용을 긁어와서 넣어도 됨.
	private Criteria cri; // page, perPageNum 을 가지고 있음. 변수 4개 가지고 있음
	 
    private int totalCount; // 전체 게시글 수. 가장 중요한 변수. 모든 변수 세팅이 가능

    //[11][12][13].......[20] : 현제 페이지가 13일 때 startPage는 11, endPage는 20
    private int startPage; // 게시글 번호에 따른 (보여지는)페이지의 시작 번호
    private int endPage; // 게시글 번호에 따른 (보여지는)페이지의 마지막 번호
    // 예) startPage:1 endPage:10 ====> 1 2 3 4 5 6 7 8 9 10
 	// 예) startPage:1 endPage:5 ====> 1 2 3 4 5
 	// 예) startPage:11 endPage:20 ====> 11 12 13 14 15 16 17 18 19 20
    
    private boolean prev; // 이전 버튼을 누를 수 있는 경우/없는 경우 분류를 위함. << >> 이런거 출력 버튼여부
    private boolean next;
 
    private int displayPageNum = 10; // 화면 하단에 보여지는 페이지의 개수
    private int tempEndPage; //마지막 페이지를 담아두기 위한 변수
 
    ///////////////////////////////////////////////////////////////////////////////////////////////
    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
 
        calcData(); // 전체 필드 변수들 세팅 : 전체 게시글 수의 setter가 호출될 때 전체 세팅되도록 함
    }
 
    private void calcData() { // 전체 필드 변수 값들을 계산하는 메서드. 이 함수에만 쓰기 위해 private를 사용
    	
    	//뿌려지는 페이지 수
        endPage = (int) (Math.ceil(cri.getPage() / (double) displayPageNum) * displayPageNum);
        // 현재페이지 정보로 부터 끝페이지에 대한 연산
     	
        // ex) 현재 페이지(pageInfo.getPage()) = 3
     	// 하단 페이징 바에 보여줄 페이징바 개수 = displayPageNum = 10개
     	// 연산 : ceil(3 / 10.0) * 10 => 1 * 10 = 10까지
     	// 1. (3/10.0) = 0.3
     	// 2. ceil(0.3) = 1 (소수점 올림)
     	// 3. 1* 10 = 10
     	// 1 2 3 4 5 6 7 8 9 10
        
        // ex2) 현재 페이지 (pageInfo.getPage()) = 13
     	// 하단 페이징 바에 보여줄 페이징바 개수 = diplayPageNum = 10개
     	// 연산 : ceil(13/10.0) * 10 => 2 * 10 = 20까지
     	// 11 12 13 14 15 16 17 18 19 20
        
        startPage = (endPage - displayPageNum) + 1;
        // 끝페이지 정보로 부터 현재 페이지에 대한 연산
        
     	// ex) 현재페이지 pageInfo.getPage()) = 3
     	// 끝페이지 endPage = 10
     	// 하단 페이징 바에 보여줄 페이징바 개수 = displayPageNum = 10
     	// 시작페이지 = (10 - 10) + 1 = 1

     	// 현재페이지 3으로부터 endPage를 연산한후(10)
     	// 해당 연산결과로 시작페이지의 값을 연산

     	// ex) 현재페이지 pageInfo.getPage()) = 13
     	// 끝페이지 endPage = 20
     	// 하단 페이징 바에 보여줄 페이징바 개수 = displayPageNum = 10
     	// 시작페이지 = (20 - 10) + 1 = 11
        
        //우리가 게시글을 10개만 뿌린다고 가정했을 때 10개가 안되는 게시글 처리하는 코드
        int tempEndPage = (int) (Math.ceil(totalCount / (double) cri.getPerPageNum()));
        // 전체 총 페이지수 :
     	// totalCount = select count(*) from tbl_count의 결과값
     	// pageInfo.getPerPageNum = 한 화면에 출력할 행의 개수
     	// ex) totalCount = 512
     	// pageInfo.getPerPageNum = 10
     	// tempEndPage = ceil(512 / 10.0) = 52
     	// 51.2의결과를 올림 => 51페이지 까지는 한 화면당 10개의 게시물이 출력
     	// 마지막 52페이지에는 0.2에 해당하는 2개의 게시물이 출력
        
        this.tempEndPage = tempEndPage;
        
        if (endPage > tempEndPage) {
            endPage = tempEndPage;
        }
 
        prev = startPage == 1 ? false : true; // 1페이지면 이전 누를 수 없게 false
        // ====> 만약 1~10페이지를 출력할 때는 좌측의 '<<'버튼 생략
        
        next = endPage * cri.getPerPageNum() >= totalCount ? false : true;
        // ====> 총 100페이지, 출력하는 페이지가 90~100일때 우측의 '>>'버튼 생략
    }
 
    // getter setter
 
    public Criteria getCri() {
        return cri;
    }
 
    public int getTempEndPage() {
        return tempEndPage;
    }
 
    public void setCri(Criteria cri) {
        this.cri = cri;
    }
 
    public int getTotalCount() {
        return totalCount;
    }
 
    public int getStartPage() {
        return startPage;
    }
 
    public void setStartPage(int startPage) {
        this.startPage = startPage;
    }
 
    public int getEndPage() {
        return endPage;
    }
 
    public void setEndPage(int endPage) {
        this.endPage = endPage;
    }
 
    public boolean isPrev() {
        return prev;
    }
 
    public void setPrev(boolean prev) {
        this.prev = prev;
    }
 
    public boolean isNext() {
        return next;
    }
 
    public void setNext(boolean next) {
        this.next = next;
    }
 
    public int getDisplayPageNum() {
        return displayPageNum;
    }
 
    public void setDisplayPageNum(int displayPageNum) {
        this.displayPageNum = displayPageNum;
    }
    
    public String makeQuery(int page) { //페이지 번호마다 링크걸려져 있음
        UriComponents uriComponents = UriComponentsBuilder.newInstance().queryParam("page", page) // page = 3
        		.queryParam("perPageNum", cri.getPerPageNum()) // page=3&perPageNum=10
        		.build(); // ?page=3&perPageNum=10 get방식에 대한 key-value
        return uriComponents.toUriString(); // ?page=3&perPageNum=10의 값을 리턴
    }
    
    /*
	 * public String makeSearch(int page) { 
	 * UriComponents uriComponents = UriComponentsBuilder.newInstance().queryParam("page", page)
	 * .queryParam("perPageNum", cri.getPerPageNum()) 
	 * .queryParam("searchType", ((SearchCriteria) cri).getsearchType()) 
	 * .queryParam("keyword", ((SearchCriteria) cri).getKeyword()).build(); //기존 makeQuery메소드에
	 * //SearchCriteria의 속성변수를 추가하여 
	 * //쿼리생성 
	 * //최종 쿼리 형태
	 * //http://localhost/sboard/list?page=1&perPageNum=10&searchType=t&keyword=title 
	 * //?page=1&perPageNum=10&searchType=t&keyword=title 
	 * //page=cri.getPage() = 파라미터 page /
	 * /perPageNum = cri.getPerPageNum 
	 * //searchType = (SearchCriteria) cri.getsearchType() : cri의 자식클래스의 get메소드 
	 * //keyword = (SearchCriteria)cri.getKeyword() : cri의 자식 클래스의 get메소드
	 * //.build()메소드를 이용해 최종적으로
	 * //http://localhost/sboard/list?page=1&perPageNum=10&searchType=t&keyword=title return uriComponents.toUriString(); 
	 * }
	 */
}
