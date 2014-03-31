<%@ tag language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ attribute name="wrap" required="true" type="com.excilys.wrapper.PageWrapper"%>

<ul class="pagination">
     		<c:choose>
	     		<c:when test="${wrap.currentPage != 1}">
	     			<li><a href="dashboard?page=${wrap.currentPage -1}&search=${wrap.search}&orderField=${wrap.orderField}&order=${wrap.order}">&laquo;</a></li>
	     		</c:when>
	    		<c:otherwise>
		        	<li class="disabled"><a href="#">&laquo;</a></li>
		        </c:otherwise>
	        </c:choose>
            <c:forEach begin="1" end="${wrap.countPages}" var="i">
                <c:choose>
		     		<c:when test="${wrap.currentPage == i}">
		     			<li class="active"><a href="dashboard?page=${i}&search=${wrap.search}&orderField=${wrap.orderField}&order=${wrap.order}"> ${i} </a><li>
		     		</c:when>
		    		<c:otherwise>
			        	<li><a href="dashboard?page=${i}&search=${wrap.search}&orderField=${wrap.orderField}&order=${wrap.order}"> ${i} </a><li>
			        </c:otherwise>
		        </c:choose>
                
            </c:forEach>
            <c:choose>
	     		<c:when test="${wrap.currentPage lt wrap.countPages}">
	     			<li><a href="dashboard?page=${wrap.currentPage +1}&search=${wrap.search}&orderField=${wrap.orderField}&order=${wrap.order}">&raquo;</a></li>
	     		</c:when>
	    		<c:otherwise>
		        	<li class="disabled"><a href="#">&raquo;</a></li>
		        </c:otherwise>
	        </c:choose>
    </ul>