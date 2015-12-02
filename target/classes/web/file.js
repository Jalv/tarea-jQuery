var API_BASE_URL = "http://localhost:8080/myapp/file";

$("#button_list").click(function(e) {
	e.preventDefault();
	getRepos();
});

$("#button_get").click(function(e) {
	e.preventDefault();
	getRepo($("#file_name").val());

});

$("#button_put").click(function(e) {
	e.preventDefault();

    var newFile = new Object();
	newFile.name = $("#file_name").val()
	newFile.creationdate = $("#fecha").val()
    newFile.description = $("#descripcion").val()
    newFile.size = $("#tamano").val()
    newFile.url = $("#url").val()
	
	updateRepo(newFile);
});

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


$("#button_delete").click(function(e) {
    e.preventDefault();
    deleteRepo($("#file_name").val());
});

$("#button_pagination").click(function(e){
	var url = API_BASE_URL + '/pagination?page=' + num_page;
	getlistpag(url);
});



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
		$.each(this.repos, function(i, v) {
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

function getRepos() {
	var url = API_BASE_URL + '/list';
	$("#result").text('');
	
	$.ajax({
		url : url,
		type : 'GET',
		crossDomain : true,
		dataType : 'json',
	}).done(function(data, status, jqxhr) {
				var repos = data;
				
				$.each(repos, function(i, v) {
					var repo = v;

				$('<strong> Name: ' + repo.name + '</strong>').appendTo($('#result'));
				$('<strong> Creation Date: </strong> ' + repo.creationdate + '<br>').appendTo($('#result'));
				$('<strong> URL: </strong> ' + repo.url + '<br>').appendTo($('#result'));
				$('<strong> Description: </strong> ' + repo.description + '<br>').appendTo($('#result'));
                $('<strong> Size: </strong> ' + repo.size + '<br>').appendTo($('#result'));
				});
				

	}).fail(function() {
		$("#result").text("No files");
	});

}

// Editat
function getRepo(filename) {
	var url = API_BASE_URL + '/' + filename;
	$("#result").text('');

	$.ajax({
		url : url,
		type : 'GET',
		crossDomain : true,
		dataType : 'json',
	}).done(function(data, status, jqxhr) {

				var repo = data;

				$("#result").text('');
				$('<strong> Name:</strong> ' + repo.name + '<br>').appendTo($('#result'));
				$('<strong> Creation Date:</strong> ' + repo.creationdate + '<br>').appendTo($('#result'));
				$('<strong> URL: </strong> ' + repo.url + '<br>').appendTo($('#result'));
				$('<strong> Description: </strong> ' + repo.description + '<br>').appendTo($('#result'));
                $('<strong> Size: </strong> ' + repo.size + '<br>').appendTo($('#result'));

			}).fail(function() {
				$('<div class="alert alert-danger"> <strong>Oh!</strong> File not found </div>').appendTo($("#result"));
	});

}

function updateRepo(dades) {
	var url = API_BASE_URL + '/' + dades.name;
	var data = JSON.stringify(dades);

	$("#result").text('');

	$.ajax({
		url : url,
		type : 'POST',
		crossDomain : true,
		dataType : 'json',
		data : data,
		statusCode: {
    		404: function() {$('<div class="alert alert-danger"> <strong>Oh!</strong> Page not found </div>').appendTo($("#result"));}
    	}
	}).done(function(data, status, jqxhr) {
		$('<div class="alert alert-success"> <strong>Ok!</strong> File Updated</div>').appendTo($("#result"));				
  	}).fail(function() {
		$('<div class="alert alert-danger"> <strong>Oh!</strong> Error </div>').appendTo($("#result"));
	});

}
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


function deleteRepo(filename) {
	var url = API_BASE_URL + '/' + filename;
	$("#result").text('');
    
	$.ajax({
           name : filename,
           url : url,
           type : 'DELETE',
           crossDomain : true,
           dataType : 'json',
           }).done(function(data, status, jqxhr) {
                   $('<div class="alert alert-success"> <strong>OK!</strong> It works! </div>').appendTo($("#result"));;
                   }).fail(function() {
                           $('<div class="alert alert-danger"> <strong>Oh!</strong> File not found </div>').appendTo($("#result"));
                           });
    
}
