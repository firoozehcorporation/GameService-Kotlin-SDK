/*
 * <copyright file="SaveDetails.kt" company="Firoozeh Technology LTD">
 * Copyright (C) 2020. Firoozeh Technology LTD. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * </copyright>
 */

package ir.firoozehcorp.gameservice.models.basicApi

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * @author Alireza Ghodrati
 */
class SaveDetails : Serializable {

    /**
     * Gets the Game id.
     * @return the Game id
     */
    @SerializedName("game")
    @Expose
    var gameId: String? = null
        private set


    /**
     * Gets the User id.
     * @return the User id
     */
    @SerializedName("user")
    @Expose
    var userId: String? = null
        private set


    /**
     * Gets the Save Name.
     * @return the Save Name
     */
    @SerializedName("name")
    @Expose
    var name: String? = null
        private set


    /**
     * Gets the Last Modify Save Time.
     * @return the Last Modify Save Time
     */
    @SerializedName("lastmodify")
    @Expose
    var lastModify: String? = null
        private set


    override fun toString(): String {
        return "SaveDetails(gameId=$gameId, userId=$userId, name=$name, lastModify=$lastModify)"
    }


}