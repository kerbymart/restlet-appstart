package org.example.resources;

import com.wordnik.swagger.annotations.*;
import org.restlet.resource.Post;

@Api(value = "/hello")
public interface HelloWorldResource {
    @Post
    @ApiOperation(value = "Get a hello message",
            notes = "Returns a hello message. You can specify a name as a query parameter or in Greeting JSON format.",
            response = String.class)
    String postHello(@ApiParam(value = "Greeting JSON in String format", required = true) String greeting);
}
