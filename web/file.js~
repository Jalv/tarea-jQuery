var API_BASE_URL = "http://localhost:8080/myapp/file";

$("#button_list").click(function(e) {
	e.preventDefault();
	getRepos();
});

function getRepos() {
	var url = API_BASE_URL + '/list';
	$("#result").text('');

	$.ajax({
		url : url,
		type : 'GET',
		crossDomain : true,
		dataType : 'json',
	}).done(function(data, status, jqxhr) {
		var f = data.files;

		$.each(f, function(i, v) {
			var file = v;
			$('<strong> Name: </strong>' + file.name +'<br>' ).appendTo($('#result'));
			$('<strong> Description: </strong> ' + file.description + '<br>').appendTo($('#result'));
			$('<strong> Creation Date: </strong> ' + file.creationdate + '<br>').appendTo($('#result'));
			$('<strong> Size: </strong> ' + file.size + '<br>').appendTo($('#result'));
			$('<strong> Tags: </strong> ' + file.taglist + '<br>').appendTo($('#result'));
			$('<strong> URL: </strong> ' + file.url + '<br>').appendTo($('#result'));
			$('<div class="panel panel-default"> <div class="panel-body"></div> </div>').appendTo($('#result'));
		});
	}).fail(function() {
		$("#result").text("No files");
	});

}

/*--------------------------------------------------------------------------------------------------------------*/

$("#button_get").click(function(e) {
	e.preventDefault();
	getRepo();

});

function getRepo() {
	var url = API_BASE_URL + '/' + $("#file_name").val();
	$("#result").text('');

	$.ajax({
		url : url,
		type : 'GET',
		crossDomain : true,
		dataType : 'json',
	}).done(function(data, status, jqxhr) {

		var file = data;

		$("#result").text('');
		$('<strong> Name:</strong> ' + file.name + '<br>').appendTo($('#result'));
		$('<strong> Creation Date:</strong> ' + file.creationdate + '<br>').appendTo($('#result'));
		$('<strong> URL: </strong> ' + file.url + '<br>').appendTo($('#result'));
		$('<strong> Description: </strong> ' + repo.description + '<br>').appendTo($('#result'));
		$('<strong> Size: </strong> ' + file.size + '<br><br><br>').appendTo($('#result'));

	}).fail(function() {
		$('<div class="alert alert-danger"> <strong>Oh!</strong> File not found </div>').appendTo($("#result"));
	});

}

/*--------------------------------------------------------------------------------------------------------------*/

$("#button_delete").click(function(e) {
	e.preventDefault();
	deleteRepo();
});

function deleteRepo(){
	var url = API_BASE_URL + '/' + $("#file_name").val();
	$("#result").text('');

	$.ajax({
		url: url,
		type: 'DELETE',
		crossDomain: true,
		dataType: 'json',
		statusCode: {
			202: function() {
				$('<div class="alert alert-success"> <strong>Ok!</strong> File Deleted</div>').appendTo($("#result"));
			},
			404: function () {
				$('<div class="alert alert-danger"> <strong>Oh!</strong> File not found </div>').appendTo($('#result'));
			}
		}
	})
}

/*--------------------------------------------------------------------------------------------------------------*/

$("#button_post").click(function(e) {
	e.preventDefault();

	var newFile = new Object();
	newFile.name = $("#file_name").val();
	newFile.description = $("#descripcion").val();
	newFile.creationdate = $("#fecha").val();
	newFile.taglist =$("#tags").val();
	newFile.size = $("#tamano").val();
	newFile.url = $("#url").val();

	createRepo(newFile);
});

function createRepo(dades) {
	var url = API_BASE_URL;
	var data = JSON.stringify(dades);
	console.log(dades);

	$("#result").text('');

	$.ajax({
		url : url,
		type : 'POST',
		crossDomain : true,
		dataType : 'json',
		contentType:'application/json',
		data : data,
	}).done(function(data, status, jqxhr) {
		$('<div class="alert alert-success"> <strong>Ok!</strong> File Created</div>').appendTo($("#result"));
	}).fail(function() {
		$('<div class="alert alert-danger"> <strong>Oh!</strong> Error </div>').appendTo($("#result"));
	});

}

/*--------------------------------------------------------------------------------------------------------------*/

$("#button_put").click(function(e){
	e.preventDefault();

	var newFile = new Object();
	newFile.name = $("#file_name").val();
	newFile.description = $("#descripcion").val();
	newFile.creationdate = $("#fecha").val();
	newFile.taglist =$("#tags").val();
	newFile.size = $("#tamano").val();
	newFile.url = $("#url").val();

	updateRepo(newFile);
});

function updateRepo(newFile) {
	var url = API_BASE_URL + '/' + $("#file_name").val();
	var data = JSON.stringify(newFile);
	$("#result").text('');

	$.ajax({
		url: url,
		type: 'PUT',
		crossDomain: true,
		dataType: 'json',
		contentType:'application/json',
		data: data,
	}).done(function (data, status, jqxhr) {
		$('<div class="alert alert-success"> <strong>Ok!</strong> File Updated</div>').appendTo($("#result"));
	}).fail(function () {
		$("#result").text("Error NO ACTUALIZADO!");
	});
}

/*--------------------------------------------------------------------------------------------------------------*/

$("#button_pagination").click(function(e){
	var url = API_BASE_URL + '/pagination?page=0';
	getlistpag(url);
});

function getlistpag(url) {
	$("#result").text('');

	$.ajax({
		url : url,
		type : 'GET',
		crossDomain : true,
		dataType : 'json',
	}).done(function(data, status, jqxhr) {
		var response = data;
		var repoCollection = new RepoCollection(response);
		var linkHeader = jqxhr.getResponseHeader('Link');

		repoCollection.buildLinks(linkHeader);
		console.log(linkHeader);

		var html = repoCollection.toHTML();
		$("#result").html(html);

	}).fail(function(jqXHR, textStatus) {
		console.log(textStatus);
	});

}

function RepoCollection(repoCollection){
	this.repos = repoCollection;
	var href = {};

	var instance = this;

	this.buildLinks = function(header){
		this.links = weblinking.parseHeader(header);
	}

	this.getLink = function(rel){
		return this.links.getLinkValuesByRel(rel);
	}

	this.toHTML = function(){
		var html = '';
		$.each(this.repos.files, function(i, v) {
			var repo = v;
			console.log(repo);
			html = html.concat('<br><strong> Name: ' + repo.name + '</strong><br>');

		});

		html = html.concat(' <br> ');

		var prev = this.getLink('prev');
		if (prev.length == 1) {
			console.log(prev[0].href);
			html = html.concat(' <a onClick="getlistpag(\'' + prev[0].href + '\');" style="cursor: pointer; cursor: hand;">[Prev]</a> ');
		}
		var next = this.getLink('next');
		if (next.length == 1) {
			html = html.concat(' <a onClick="getlistpag(\'' + next[0].href + '\');" style="cursor: pointer; cursor: hand;">[Next]</a> ');
		}

		return html;
	}

}

/*--------------------------------------------------------------------------------------------------------------*/
