<jsp:include page="include/header.jsp" />
<section id="main">
	<h1>Add Computer</h1>
	<form class="form-horizontal" action="addComputer" method="POST">
		<div class="control-group">
			<label class="control-label" for="name">Computer name:</label>
			<div class="controls">
				<input type="text" id="inputName" name="name" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label" for="introduced">Introduced date
				:</label>
			<div class="controls">
				<input type="date" id="inputIntroduced" name="introduced">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label" for="discontinued">Discontinued
				date :</label>
			<div class="controls">
				<input type="date" id="inputDiscontinued" name="discontinued">
			</div>
		</div>
		<!-- à faire!!! -->
		<div class="clearfix">
			<label for="company">Company Name:</label>
			<div class="input">
				<select name="company">
					<option value="0">--</option>
					<option value="1">Apple</option>
					<option value="2">Dell</option>
					<option value="3">Lenovo</option>
				</select>
			</div>
		</div>
		<div class="actions">
			<input type="submit" value="Add" class="btn primary"> or <a
				href="dashboard.jsp" class="btn">Cancel</a>
		</div>
		<!-- Jusqu'ici!!!!  -->
	</form>
</section>

<jsp:include page="include/footer.jsp" />