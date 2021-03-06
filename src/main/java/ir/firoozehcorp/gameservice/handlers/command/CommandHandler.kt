/*
 * <copyright file="CommandHandler.kt" company="Firoozeh Technology LTD">
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

package ir.firoozehcorp.gameservice.handlers.command

import ir.firoozehcorp.gameservice.core.GameService
import ir.firoozehcorp.gameservice.core.sockets.GsSocketClient
import ir.firoozehcorp.gameservice.core.sockets.GsTcpClient
import ir.firoozehcorp.gameservice.handlers.HandlerCore
import ir.firoozehcorp.gameservice.handlers.command.request.*
import ir.firoozehcorp.gameservice.handlers.command.request.chat.*
import ir.firoozehcorp.gameservice.handlers.command.request.chat.GetChannelRecentMessagesRequestHandler
import ir.firoozehcorp.gameservice.handlers.command.request.chat.GetChannelsSubscribedHandler
import ir.firoozehcorp.gameservice.handlers.command.request.chat.SendChannelMessageHandler
import ir.firoozehcorp.gameservice.handlers.command.request.chat.SubscribeChannelHandler
import ir.firoozehcorp.gameservice.handlers.command.request.chat.UnSubscribeChannelHandler
import ir.firoozehcorp.gameservice.handlers.command.resposne.*
import ir.firoozehcorp.gameservice.handlers.command.resposne.chat.*
import ir.firoozehcorp.gameservice.handlers.command.resposne.chat.ChannelMessageResponseHandler
import ir.firoozehcorp.gameservice.handlers.command.resposne.chat.ChannelRecentResponseHandler
import ir.firoozehcorp.gameservice.handlers.command.resposne.chat.ChannelSubscribedResponseHandler
import ir.firoozehcorp.gameservice.handlers.command.resposne.chat.SubscribeChannelResponseHandler
import ir.firoozehcorp.gameservice.handlers.command.resposne.chat.UnSubscribeChannelResponseHandler
import ir.firoozehcorp.gameservice.handlers.realtime.request.SendPublicMessageHandler
import ir.firoozehcorp.gameservice.models.GameServiceException
import ir.firoozehcorp.gameservice.models.consts.Command
import ir.firoozehcorp.gameservice.models.enums.gsLive.GProtocolSendType
import ir.firoozehcorp.gameservice.models.enums.gsLive.GSLiveType
import ir.firoozehcorp.gameservice.models.gsLive.APacket
import ir.firoozehcorp.gameservice.models.gsLive.command.Packet
import ir.firoozehcorp.gameservice.models.internal.interfaces.GameServiceCallback
import ir.firoozehcorp.gameservice.models.listeners.CoreListeners
import ir.firoozehcorp.gameservice.utils.GsLiveSystemObserver

/**
 * @author Alireza Ghodrati
 */
internal class CommandHandler : HandlerCore() {

    private val tcpClient: GsTcpClient = GsTcpClient(Command.area)

    private lateinit var responseHandlers: MutableMap<Int, IResponseHandler>
    private lateinit var requestHandlers: MutableMap<String, IRequestHandler>


    companion object {
        var PlayerHash: String = ""
    }

    init {
        observer = GsLiveSystemObserver(GSLiveType.Core)

        setListeners()

        initRequestMessageHandlers()
        initResponseMessageHandlers()
    }


    private fun setListeners() {

        tcpClient.onError += object : GsSocketClient.ErrorListener {
            override fun invoke(element: GameServiceException, from: Class<*>?) {
                if (disposed) return
                init()
            }
        }
        tcpClient.onDataReceived += object : GsSocketClient.DataReceivedListener {
            override fun invoke(element: Packet, from: Class<*>?) {
                responseHandlers[element.action]?.handlePacket(element, gson)
            }
        }

        CoreListeners.Ping += object : CoreListeners.PingListener {
            override fun invoke(element: Void?, from: Class<*>?) {
                if (from != PingResponseHandler::class.java) return
                request(PingPongHandler.signature)
            }
        }
        CoreListeners.Authorized += object : CoreListeners.AuthorisationListener {
            override fun invoke(element: String, from: Class<*>?) {
                if (from != AuthResponseHandler::class.java) return
                PlayerHash = element
                tcpClient.updatePwd(element)

                if (isFirstInit) return
                isFirstInit = true
                CoreListeners.SuccessfullyLogined.invokeListeners(null)
            }
        }

    }

    private fun initRequestMessageHandlers() {
        requestHandlers = mutableMapOf(
                AcceptInviteHandler.signature to AcceptInviteHandler(),
                AuthorizationHandler.signature to AuthorizationHandler(),
                AutoMatchHandler.signature to AutoMatchHandler(),
                CreateRoomHandler.signature to CreateRoomHandler(),
                CancelAutoMatchHandler.signature to CancelAutoMatchHandler(),
                FindUserHandler.signature to FindUserHandler(),
                GetRoomsHandler.signature to GetRoomsHandler(),
                InviteListHandler.signature to InviteListHandler(),
                InviteUserHandler.signature to InviteUserHandler(),
                JoinRoomHandler.signature to JoinRoomHandler(),
                PingPongHandler.signature to PingPongHandler(),

                SubscribeChannelHandler.signature to SubscribeChannelHandler(),
                UnSubscribeChannelHandler.signature to UnSubscribeChannelHandler(),
                GetChannelsSubscribedHandler.signature to GetChannelsSubscribedHandler(),
                GetPendingMessagesRequestHandler.signature to GetPendingMessagesRequestHandler(),
                GetChannelRecentMessagesRequestHandler.signature to GetChannelRecentMessagesRequestHandler(),
                GetChannelsMembersRequestHandler.signature to GetChannelsMembersRequestHandler(),
                SendPrivateMessageHandler.signature to SendPrivateMessageHandler(),
                SendChannelMessageHandler.signature to SendChannelMessageHandler()
        )
    }

    private fun initResponseMessageHandlers() {
        responseHandlers = mutableMapOf(
                AutoMatchResponseHandler.action to AutoMatchResponseHandler(),
                AuthResponseHandler.action to AuthResponseHandler(),
                CancelAutoMatchResponseHandler.action to CancelAutoMatchResponseHandler(),
                ErrorResponseHandler.action to ErrorResponseHandler(),
                FindMembersResponseHandler.action to FindMembersResponseHandler(),
                GetRoomResponseHandler.action to GetRoomResponseHandler(),
                GetInviteInboxResponseHandler.action to GetInviteInboxResponseHandler(),
                InviteReceivedResponseHandler.action to InviteReceivedResponseHandler(),
                InviteUserResponseHandler.action to InviteUserResponseHandler(),
                JoinRoomResponseHandler.action to JoinRoomResponseHandler(),
                NotificationResponseHandler.action to NotificationResponseHandler(),
                PingResponseHandler.action to PingResponseHandler(),

                SubscribeChannelResponseHandler.action to SubscribeChannelResponseHandler(),
                UnSubscribeChannelResponseHandler.action to UnSubscribeChannelResponseHandler(),
                ChannelSubscribedResponseHandler.action to ChannelSubscribedResponseHandler(),
                ChannelMessageResponseHandler.action to ChannelMessageResponseHandler(),
                ChannelRecentResponseHandler.action to ChannelRecentResponseHandler(),
                ChannelsMembersResponseHandler.action to ChannelsMembersResponseHandler(),
                PendingMessagesResponseHandler.action to PendingMessagesResponseHandler(),
                PrivateChatResponseHandler.action to PrivateChatResponseHandler()
        )
    }


    public override fun init() {
        tcpClient.init(GameService.CommandInfo,object : GameServiceCallback<Boolean> {
            override fun onFailure(error: GameServiceException) {}
            override fun onResponse(response: Boolean) {
                tcpClient.startReceiving()
                request(AuthorizationHandler.signature)
            }
        })
    }

    public override fun request(handlerName: String, payload: Any?) {
        requestHandlers[handlerName]?.handleAction(payload)?.let { send(it) }
    }

    override fun request(handlerName: String, payload: Any?, type: GProtocolSendType) {

    }

    override fun send(packet: APacket) {
        if (!observer.increase()) return
        tcpClient.send(packet)
    }

    override fun send(packet: APacket, type: GProtocolSendType) {

    }


    override fun close() {
        disposed = true
        isFirstInit = false
        tcpClient.stopReceiving()
        observer.dispose()
    }



}