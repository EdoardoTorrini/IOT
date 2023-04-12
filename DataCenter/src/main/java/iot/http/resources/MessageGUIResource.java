package iot.http.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.container.ContainerRequestContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.codahale.metrics.annotation.Timed;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import com.google.gson.Gson;

import io.dropwizard.jersey.errors.ErrorMessage;

import javax.ws.rs.core.Response;

import iot.data_center.models.MessageGUIModel;
import iot.data_center.persistance.MessageGUIManager;

@Path("/")
public class MessageGUIResource {

    private static final Logger logger = LoggerFactory.getLogger(MessageGUIResource.class);
    private MessageGUIManager msg;
    private Gson gson;

    public MessageGUIResource(MessageGUIManager msg) {
        this.msg = msg;
        this.gson = new Gson();
    }

    @GET
    @Path("/status")
    @Timed
    @Produces(MediaType.APPLICATION_JSON)
    public Response getStatus(@Context ContainerRequestContext req) {
        try {
            return Response.ok(
                this.gson.toJson(this.msg.getMsgGUIModel(), 
                MessageGUIModel.class)
            ).header("Access-Control-Allow-Origin", "*").build();
        }
        catch (Exception eErr) {
            logger.error("[ MESSAGE GUI RESOURCES ] -> [ MESSAGE ]", eErr.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).type(MediaType.APPLICATION_JSON).entity(new ErrorMessage(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), "Internal Server Error!")).build();
        }
    }
}
