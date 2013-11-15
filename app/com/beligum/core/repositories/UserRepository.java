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
package com.beligum.core.repositories;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.PersistenceException;


import com.google.common.base.Predicate;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import play.Logger;


import com.avaje.ebean.Ebean;
import com.avaje.ebean.PagingList;
import com.beligum.core.models.User;
import com.beligum.core.utils.security.UserRoles;

import static com.google.common.collect.Lists.newArrayList;

public class UserRepository {
    private static Set<User> users = Sets.newHashSet(
            new User("admin", "admin", "admin", "admin", UserRoles.ROOT_ROLE));//TODO sphere

    public static User save(User user) throws PersistenceException
    {
	try {
	    users.add(user);
	    return user;
	} catch (Exception e) {
	    Logger.error("Caught error while saving a user", e);
	    throw new PersistenceException(e);
	}
    }

    public static User update(User user) throws PersistenceException
    {
	try {
        users.add(user);
	    return user;
	} catch (Exception e) {
	    Logger.error("Caught error while updating a user", e);
	    throw new PersistenceException(e);
	}
    }

    public static void delete(User user) throws PersistenceException
    {
	try {
	    users.remove(user);
	} catch (Exception e) {
	    Logger.error("Caught error while deleting a user", e);
	    throw new PersistenceException(e);
	}
    }

    public static PagingList<User> findPage(String search, Integer size) throws PersistenceException {
        try {
//	    search = "%" + search + "%";
//	    return Ebean.find(User.class).where().ilike("email", search).findPagingList(size);
            throw new RuntimeException("com.beligum.core.repositories.UserRepository#findPage not implemented ");
        } catch (Exception e) {
            Logger.error("Caught error while searching a user by page", e);
            throw new PersistenceException(e);
        }
    }

    public static User find(final long id) throws PersistenceException
    {
	try {
        ArrayList<User> userArrayList = newArrayList(Sets.filter(users, new Predicate<User>() {
            @Override
            public boolean apply(User user) {
                return user.getId() == id;
            }
        }));


        return userArrayList.get(0);
	} catch (Exception e) {
	    Logger.error("Caught error while searching a user", e);
	    throw new PersistenceException(e);
	}
    }

    public static User findByEmail(final String email) throws PersistenceException
    {
	try {
        ArrayList<User> userArrayList = newArrayList(Sets.filter(users, new Predicate<User>() {
            @Override
            public boolean apply(User user) {
                return user.getEmail().equals(email);
            }
        }));


        return userArrayList.size() == 1 ? userArrayList.get(0) : null;
	} catch (Exception e) {
	    Logger.error("Caught error while searching a user by login", e);
	    throw new PersistenceException(e);
	}
    }

    public static List<User> findAll() throws PersistenceException
    {
	try {
	    return newArrayList(users);
	} catch (Exception e) {
	    Logger.error("Caught error while searching all users", e);
	    throw new PersistenceException(e);
	}
    }
    
    public static boolean RootExists() throws PersistenceException
    {
	boolean retVal = true; 
//	try {
//	    int rootUserCount = Ebean.find(User.class).where().eq("roleLevel", UserRoles.ROOT_ROLE.getLevel()).findRowCount();
//	    if (rootUserCount == 0) {
//		retVal = false;
//	    }
//	} catch (Exception e) {
//	    Logger.error("Caught error while searching all users", e);
//	    throw new PersistenceException(e);
//	}
	return retVal;
    }
    
    public static int UserCount() throws PersistenceException
    {
	int totalNrOfRows = 0;
	try {
	    totalNrOfRows = Ebean.find(User.class).findRowCount();
	    
	} catch (Exception e) {
	    Logger.error("Caught error while searching all users", e);
	    throw new PersistenceException(e);
	}
	return totalNrOfRows;
    }

    public static void refreshUser(User user) throws PersistenceException
    {
	try {
	    Ebean.refresh(user);
	} catch (Exception e) {
	    Logger.error("Caught error while refreshing a user", e);
	    throw new PersistenceException(e);
	}
    }

}