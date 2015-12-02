package edu.upc.eetac.dsa;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Path("/file")
public class FileResource {


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getFile() {
        File file = new File();
        file.setName("test");
        file.setDescription("Fichero de prueba");
        file.setUrl("http://prova/test");
        return Response.ok(file).build();
    }

    
    @GET @Path("/{filename}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getFilebyName(@PathParam("filename") String filename) {
        File file = FileDao.instance.getModel().get(filename);
        Response response;
        
        if(file==null) {
        	response = Response.status(Response.Status.NOT_FOUND).build();
            
        } else {
        	response = Response.ok(file).build();
        }
        return response;
    }
    

    @GET @Path("/list")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getFiles() {
    	List<File> values = new ArrayList<File>();
        values.addAll(FileDao.instance.getModel().values());
        FileList files = new FileList();
        files.setFiles(values);

        return Response.ok(files).build();
    }

   
    @POST 
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response newFileInJSON(File newfile) {

		if (newfile.getName() == null) {
			return Response.status(Response.Status.BAD_REQUEST).build();
		}
		if (newfile.getUrl() == null) {
			return Response.status(Response.Status.BAD_REQUEST).build();
		}

		File file = FileDao.instance.getModel().get(newfile.getName());
		if (file != null) {
			return Response.status(Response.Status.CONFLICT).build();
		}

		if (newfile.getCreationdate() == null) {
			Date now = new Date();
			newfile.setCreationdate(now);
		}
		
		FileDao.instance.getModel().put(newfile.getName(), newfile);

		return Response.ok(newfile).build();
    }
   
    
    @PUT  @Path("/{filename}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateFileInJSON(@PathParam("filename") String filename, File newfile) {
        if (newfile.getName()==null){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        if (newfile.getUrl()==null){
           return Response.status(Response.Status.BAD_REQUEST).build();
        }
        if(!filename.equals(newfile.getName())) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        File file = FileDao.instance.getModel().get(filename);
        if(file==null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        
        FileDao.instance.getModel().put(newfile.getName(), newfile);

        return Response.ok(file).build();

    }

    
	@DELETE
	@Path("/{filename}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteFilebyId(@PathParam("filename") String filename) {
		File file = FileDao.instance.getModel().get(filename);
		if (file == null) {
			return Response.status(Response.Status.NOT_FOUND).build();
		}
		
		file = FileDao.instance.getModel().remove(filename);
		return Response.status(Response.Status.ACCEPTED).build();

	}

	
	
	@GET @Path("/pagination")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getpaginationlist(@QueryParam("page") int page) {
		
    	List<File> values = new ArrayList<File>();
        values.addAll(FileDao.instance.getModel().values());
        int size = values.size();
        
        if (page > size) {
			return Response.status(Response.Status.NOT_FOUND).build();
		}
        if (page < 0) {
			return Response.status(Response.Status.NOT_FOUND).build();
		}

        FileList files = new FileList();
        files.setTodo(values.get((page)));
        
        String link = "";
        if (page == 0){
        	link = "<http://localhost:8080/myapp/file/pagination?page=" + (page+1) + ">; rel=\"next\"";
        }
        
        if (page == size-1){
        	link = "<http://localhost:8080/myapp/file/pagination?page=" + (page-1) + ">; rel=\"prev\"";
        }
        
        if (page < size-1 && page > 0){
        	link = "<http://localhost:8080/myapp/file/pagination?page=" + (page-1) + ">; rel=\"prev\", " + "<http://localhost:8080/myapp/file/pagination?page=" + (page+1) + ">; rel=\"next\"";
        }
        
        return Response.ok(files).header("Link", link).build();
    }
    
}


