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
package org.ngrinder.perftest.service.samplinglistener;

import static org.ngrinder.common.util.Preconditions.checkNotNull;

import java.io.File;
import java.util.Set;
import java.util.Timer;

import net.grinder.SingleConsole.SamplingLifeCycleFollowUpListener;
import net.grinder.statistics.StatisticsSet;

import org.ngrinder.model.AgentInfo;
import org.ngrinder.perftest.service.monitor.MonitorTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

/**
 * Monitor data collector.
 * 
 * @author JunHo Yoon
 * @since 3.1.1
 */
public class MonitorCollectorListener implements SamplingLifeCycleFollowUpListener {
	private static final Logger LOGGER = LoggerFactory.getLogger(MonitorCollectorListener.class);
	private final ApplicationContext applicationContext;
	private final Set<AgentInfo> agents;
	private MonitorTask monitorTask;
	private final File reportPath;
	private Timer timer;
	private final Long perfTestId;

	/**
	 * Constructor.
	 * 
	 * @param applicationContext
	 *            application context
	 * @param perfTestId
	 *            perf test id
	 * @param agents
	 *            set of monitors to be collected
	 * @param reportPath
	 *            where does it report.
	 */
	public MonitorCollectorListener(ApplicationContext applicationContext, Long perfTestId, Set<AgentInfo> agents,
			File reportPath) {
		this.applicationContext = applicationContext;
		this.perfTestId = checkNotNull(perfTestId);
		this.agents = agents;
		this.reportPath = reportPath;
	}

	@Override
	public void onSamplingStarted() {
		monitorTask = applicationContext.getBean(MonitorTask.class);
		monitorTask.setCorrespondingPerfTestId(perfTestId);
		monitorTask.add(agents, reportPath);
		if (this.timer != null) {
			timer.cancel();
		}
		this.timer = new Timer(true);
		this.timer.schedule(monitorTask, 1000, 1000);
	}

	@Override
	public void onSampling(File file, StatisticsSet intervalStatistics, StatisticsSet cumulativeStatistics,
			boolean lastCall) {
		if (monitorTask != null) {
			if (lastCall) {
				monitorTask.saveData();
			} else {
				monitorTask.saveData(true);
			}
		}
	}

	@Override
	public void onSamplingEnded() {
		LOGGER.info("remove monitors on {}", agents);
		if (timer != null) {
			timer.cancel();
		}
		if (monitorTask != null) {
			monitorTask.destroy();
		}
	}
}
