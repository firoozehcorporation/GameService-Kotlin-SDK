/*
 * <copyright file="GetRoomResponseHandler.kt" company="Firoozeh Technology LTD">
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

package ir.firoozehcorp.gameservice.handlers.command.resposne

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ir.firoozehcorp.gameservice.models.consts.Command
import ir.firoozehcorp.gameservice.models.gsLive.Room
import ir.firoozehcorp.gameservice.models.gsLive.command.Packet
import ir.firoozehcorp.gameservice.models.listeners.CommandListeners

/**
 * @author Alireza Ghodrati
 */
internal class GetRoomResponseHandler : BaseResponseHandler() {

    companion object {
        const val action = Command.ActionGetRooms
    }

    override fun handleResponse(packet: Packet, jsonHandler: Gson) {
        val rooms = jsonHandler.fromJson<MutableList<Room>>(packet.data, object : TypeToken<MutableList<Room>>() {}.type)
        CommandListeners.AvailableRoomsReceived.invokeListeners(rooms)
    }
}