/* 
 * Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License. 
 */
package org.ngrinder.agent.repository;

import java.util.List;

import net.grinder.message.console.AgentControllerState;

import org.ngrinder.model.AgentInfo;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * Agent Repository. This is necessary due to lack of agent info in the Grinder.
 * We need to keep the additional info for each agent in the DB not memory.
 * 
 * @author Tobi
 * @author JunHo Yoon
 * @since 3.0
 */
@Repository
public interface AgentManagerRepository extends JpaRepository<AgentInfo, Long>, JpaSpecificationExecutor<AgentInfo> {

	/**
	 * Find a {@link AgentInfo} by ip and host name.
	 * 
	 * @param ip
	 *            ip
	 * @param hostName
	 *            host name
	 * @return found agent
	 */
	AgentInfo findByIpAndHostName(String ip, String hostName);

	/**
	 * Find a {@link AgentInfo} by ip.
	 * 
	 * @param ip
	 *            ip of agent
	 * @return found {@link AgentInfo}
	 */
	AgentInfo findByIp(String ip);

	/**
	 * Find {@link AgentInfo}s by ip.
	 * 
	 * @param ip
	 *            ip of agent
	 * @return found {@link AgentInfo} list
	 */
	List<AgentInfo> findAllByIp(String ip);

	/**
	 * Find all agents which matches with the given the status and are approved
	 * .
	 * 
	 * @param status
	 *            AgentControllerState
	 * @param isApproved
	 *            whether the agent is approved
	 * @return agent list
	 */
	List<AgentInfo> findAllByStatusAndApproved(AgentControllerState status, boolean isApproved);

	/**
	 * Get the count of {@link AgentInfo} matching the given specification.
	 * 
	 * @param spec
	 *            Query specification
	 * @return agent count
	 */
	long count(Specification<AgentInfo> spec);
}
