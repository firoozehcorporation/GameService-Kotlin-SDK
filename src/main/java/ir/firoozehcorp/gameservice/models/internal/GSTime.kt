/*
 * <copyright file="GSTime.kt" company="Firoozeh Technology LTD">
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

import java.time.OffsetDateTime

/**
 * @author Alireza Ghodrati
 */
class GSTime {

    /**
     * Gets the Current Server Time
     * @return the Current Server Time
     */
    var serverTime: OffsetDateTime? = null


    /**
     * Gets the Current Device Time
     * @return the Current Device Time
     */
    var deviceTime: OffsetDateTime? = null


    fun isDeviceTimeValid(): Boolean {
        return serverTime?.toEpochSecond() == deviceTime?.toEpochSecond()
    }


    override fun toString(): String {
        return "GSTime(serverTime=$serverTime, deviceTime=$deviceTime)"
    }


}