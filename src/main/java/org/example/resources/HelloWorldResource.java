package org.example.resources;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiImplicitParam;
import com.wordnik.swagger.annotations.ApiImplicitParams;
import com.wordnik.swagger.annotations.ApiOperation;
import org.restlet.resource.Get;

@Api(value = "/hello")
public interface HelloWorldResource {
    @Get
    @ApiOperation(value = "Get a hello message",
            notes = "Returns a hello message. You can specify a name as a query parameter.",
            response = String.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "Name to be included in the hello message", required = false, dataType = "string", paramType = "query")
    })
    String represent();
}
