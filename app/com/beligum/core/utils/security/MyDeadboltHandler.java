/*******************************************************************************
 * Copyright (c) 2013 by Beligum b.v.b.a. (http://www.beligum.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific
 * language governing permissions and limitations under the License.
 *
 * Contributors:
 *     Beligum - initial implementation
 *******************************************************************************/
package com.beligum.core.utils.security;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Map;






import play.Logger;
import play.mvc.Http;
import play.mvc.Result;

import com.beligum.core.models.User;
import com.beligum.core.utils.FlashHelper;

import be.objectify.deadbolt.java.AbstractDeadboltHandler;
import be.objectify.deadbolt.java.DynamicResourceHandler;
import be.objectify.deadbolt.core.models.Subject;

public class MyDeadboltHandler extends AbstractDeadboltHandler
{
    public Result beforeAuthCheck(Http.Context context)
    {
	// returning null means that everything is HTTP_OK. Return a real result
	// if
	// you want a redirect to a login page or
	// somewhere else

	Http.Context.current.set(context); // <-- this is needed to have a
					   // current Http.Context

	return null;
    }

    public Subject getSubject(Http.Context context)
    {
	// in a real application, the user name would probably be in the session
	// following a login process

	return User.getCurrentUser();
    }

    public DynamicResourceHandler getDynamicResourceHandler(Http.Context context)
    {
	return new MyDynamicResourceHandler();
    }

    public Result onAuthFailure(Http.Context context, String content)
    {
	Http.Context.current.set(context); // <-- this is needed to have a
					   // current Http.Context
	Result retVal = null;
	// you can return any result from here - forbidden, etc
	FlashHelper.addError("Please log in first");
	try {
	    String onAuthFailureUrl = (String) play.Play.application().configuration().getString("beligum.core.onAuthFailureUrl");
	    if (onAuthFailureUrl != null) {
		retVal = redirect(onAuthFailureUrl);
	    } else {
		retVal = unauthorized(views.html.defaultpages.unauthorized.render());
	    }
	    // return
	    // redirect(controllers.routes.UserController.login(redirect));
	} catch (Exception e) {
	    Logger.error("UnsupportedEncodingException", e);
	    retVal = internalServerError();
	}
	return retVal;
    }
}