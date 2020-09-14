/*
 * <copyright file="Game.java" company="Firoozeh Technology LTD">
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
package ir.firoozehcorp.gameservice.models.internal

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * @author Alireza Ghodrati
 */
class Game {

    @SerializedName("id")
    @Expose
    var id: String? = null
        private set

    @SerializedName("_id")
    @Expose
    internal var _id: String? = null
        private set


    @SerializedName("name")
    @Expose
    var name: String? = null
        private set

    @SerializedName("package")
    @Expose
    var `package`: String? = null
        private set

    override fun toString(): String {
        return "Game{" +
                "Id='" + id + '\'' +
                ", _id='" + _id + '\'' +
                ", Name='" + name + '\'' +
                ", Package='" + `package` + '\'' +
                '}'
    }
}