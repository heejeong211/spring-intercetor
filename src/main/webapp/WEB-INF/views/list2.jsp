<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	
	<table width="500" cellpadding="0" cellspacing="0" border="1">
		<tr>
			<td>번호</td>
			<td>이름</td>
			<td>제목</td>
			<td>날짜</td>
			<td>히트</td>
		</tr>
		<c:forEach items="${list}" var="dto">
		<tr>
			<td>${dto.bId}</td>
			<td>${dto.bName}</td>
			<td>
				<c:forEach begin="1" end="${dto.bIndent}">></c:forEach>
				<a href="content_view?bId=${dto.bId}">${dto.bTitle}</a></td>
			<td>${dto.bDate}</td>
			<td>${dto.bHit}</td>
		</tr>
		</c:forEach>
		<tr>
			<td colspan="5"> <a href="write_view">글작성</a> </td>
		</tr>
	</table>
	
	<!-- '<<' 이전버튼 여부 -->
	<c:if test="${pageMaker.prev}"> <!-- pageMaker.getPrev 가 true이면 이전버튼이 나옴 -->
		<a href="list2${pageMaker.makeQuery(pageMaker.startPage - 1) }"> « </a> <!-- get방식의 key-value, getMackQuery 아님 다이렉트 호출임. -->
	</c:if>
	
	<!-- 페이징 숫자 뿌리는 것 -->
	<c:forEach begin="${pageMaker.startPage }" end="${pageMaker.endPage }" var="idx"> <!-- var="idx"는 1씩 증가 -->
		<c:out value="${pageMaker.cri.page == idx?'':''}" /> <!-- 에러처리 구문. 없어도 됨 -->
		<a href="list2${pageMaker.makeQuery(idx)}">${idx}</a>
	</c:forEach>
	
	<!-- '>>' 다음버튼 여부 -->
	<c:if test="${pageMaker.next && pageMaker.endPage > 0}">
		<a href="list2${pageMaker.makeQuery(pageMaker.endPage +1) }"> » </a>
	</c:if> <br />
	<!-- <button type="submit">글쓰기</button> -->
	
	
</body>
</html>