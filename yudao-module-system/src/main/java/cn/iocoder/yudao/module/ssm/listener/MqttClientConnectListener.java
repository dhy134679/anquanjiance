/*
 * Copyright (c) 2019-2029, Dreamlu 卢春梦 (596392912@qq.com & dreamlu.net).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cn.iocoder.yudao.module.ssm.listener;

import cn.iocoder.yudao.module.ssm.controller.admin.mqttlog.vo.MqttLogSaveReqVO;
import cn.iocoder.yudao.module.ssm.service.mqttlog.MqttLogService;
import org.dromara.mica.mqtt.core.client.MqttClientCreator;
import org.dromara.mica.mqtt.spring.client.event.MqttConnectedEvent;
import org.dromara.mica.mqtt.spring.client.event.MqttDisconnectEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 客户端连接状态监听
 *
 * @author L.cm
 */
@Service
public class MqttClientConnectListener {
	private static final Logger logger = LoggerFactory.getLogger(MqttClientConnectListener.class);

	@Autowired
	private MqttClientCreator mqttClientCreator;
	@Value("${spring.mqtt.key}")
	private String key;
	@Value("${spring.mqtt.client.id}")
	private String clientId;
	@Value("${spring.mqtt.username}")
	private String username;
	@Resource
	private MqttLogService mqttServerService;

	@EventListener
	public void onConnected(MqttConnectedEvent event) {
		MqttLogSaveReqVO server = new MqttLogSaveReqVO();
		server.setClientId(clientId);
		server.setUserName(username);
		server.setContext(event.toString());
		server.setType("客户端连接到MQTT服务器");
		mqttServerService.createMqttLog(server);
	}

	@EventListener
	public void onDisconnect(MqttDisconnectEvent event) {
		MqttLogSaveReqVO server = new MqttLogSaveReqVO();
		server.setClientId(clientId);
		server.setUserName(username);
		server.setContext(event.toString());
		server.setType("客户端断开了MQTT服务器");
		mqttServerService.createMqttLog(server);
	}

}
