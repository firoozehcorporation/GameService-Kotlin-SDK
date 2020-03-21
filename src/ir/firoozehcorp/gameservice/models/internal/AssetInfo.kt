/*
 * <copyright file="AssetsInfo.kt" company="Firoozeh Technology LTD">
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


class AssetInfo {

    @SerializedName("status")
    @Expose
    var status: Boolean = false
        private set


    @SerializedName("data")
    @Expose
    var assetData: AssetData? = null
        private set


    inner class AssetData {

        @SerializedName("size")
        @Expose
        var size: Long = 0
            private set


        var name: String = ""
            internal set


        @SerializedName("downloadLink")
        @Expose
        var link: String? = null
            private set

        override fun toString(): String {
            return "AssetData(size=$size, name='$name', link=$link)"
        }
    }

    override fun toString(): String {
        return "AssetInfo(status=$status, assetData=$assetData)"
    }


}
