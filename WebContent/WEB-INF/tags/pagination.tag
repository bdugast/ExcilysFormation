<%@ tag language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ attribute name="currentPage" required="true" type="Integer"%>
<%@ attribute name="countPages" required="true" type="Integer"%>
<%@ attribute name="search" required="true" type="String"%>
<%@ attribute name="orderField" required="true" type="String"%>
<%@ attribute name="order" required="true" type="String"%>

<ul class="pagination">
     		<c:choose>
	     		<c:when test="${currentPage != 1}">
	     			<li><a href="dashboard?page=${currentPage -1}&search=${search}&orderField=${orderField}&order=${order}">&laquo;</a></li>
	     		</c:when>
	    		<c:otherwise>
		        	<li class="disabled"><a href="#">&laquo;</a></li>
		        </c:otherwise>
	        </c:choose>
            <c:forEach begin="1" end="${countPages}" var="i">
                <c:choose>
		     		<c:when test="${currentPage == i}">
		     			<li class="active"><a href="dashboard?page=${i}&search=${search}&orderField=${orderField}&order=${order}"> ${i} </<li></td>
		     		</c:when>
		    		<c:otherwise>
			        	<li><a href="dashboard?page=${i}&search=${search}&orderField=${orderField}&order=${order}"> ${i} </<li></td>
			        </c:otherwise>
		        </c:choose>
                
            </c:forEach>
            <c:choose>
	     		<c:when test="${currentPage lt countPages}">
	     			<li><a href="dashboard?page=${currentPage +1}&search=${search}&orderField=${orderField}&order=${order}">&raquo;</a></li>
	     		</c:when>
	    		<c:otherwise>
		        	<li class="disabled"><a href="#">&raquo;</a></li>
		        </c:otherwise>
	        </c:choose>
    </ul>