/*
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>.
 */
package quests.Q151_CureForFeverDisease;

import net.sf.l2j.gameserver.model.actor.L2Npc;
import net.sf.l2j.gameserver.model.actor.instance.L2PcInstance;
import net.sf.l2j.gameserver.model.quest.Quest;
import net.sf.l2j.gameserver.model.quest.QuestState;
import net.sf.l2j.util.Rnd;

public class Q151_CureForFeverDisease extends Quest
{
	private static final String qn = "Q151_CureForFeverDisease";
	
	// Items
	private static final int POISON_SAC = 703;
	private static final int FEVER_MEDICINE = 704;
	
	// NPCs
	private static final int ELIAS = 30050;
	private static final int YOHANES = 30032;
	
	public Q151_CureForFeverDisease(int questId, String name, String descr)
	{
		super(questId, name, descr);
		
		questItemIds = new int[]
		{
			FEVER_MEDICINE,
			POISON_SAC
		};
		
		addStartNpc(ELIAS);
		addTalkId(ELIAS, YOHANES);
		
		addKillId(20103, 20106, 20108);
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		String htmltext = event;
		QuestState st = player.getQuestState(qn);
		if (st == null)
			return htmltext;
		
		if (event.equalsIgnoreCase("30050-03.htm"))
		{
			st.set("cond", "1");
			st.setState(STATE_STARTED);
			st.playSound(QuestState.SOUND_ACCEPT);
		}
		
		return htmltext;
	}
	
	@Override
	public String onTalk(L2Npc npc, L2PcInstance player)
	{
		String htmltext = getNoQuestMsg();
		QuestState st = player.getQuestState(qn);
		if (st == null)
			return htmltext;
		
		switch (st.getState())
		{
			case STATE_CREATED:
				if (player.getLevel() >= 15)
					htmltext = "30050-02.htm";
				else
				{
					htmltext = "30050-01.htm";
					st.exitQuest(true);
				}
				break;
			
			case STATE_STARTED:
				int cond = st.getInt("cond");
				switch (npc.getNpcId())
				{
					case ELIAS:
						if (cond == 1)
							htmltext = "30050-04.htm";
						else if (cond == 2)
							htmltext = "30050-05.htm";
						else if (cond == 3)
						{
							htmltext = "30050-06.htm";
							st.takeItems(FEVER_MEDICINE, 1);
							st.giveItems(102, 1);
							st.exitQuest(false);
							st.playSound(QuestState.SOUND_FINISH);
						}
						break;
					
					case YOHANES:
						if (cond == 2)
						{
							htmltext = "30032-01.htm";
							st.set("cond", "3");
							st.takeItems(POISON_SAC, 1);
							st.giveItems(FEVER_MEDICINE, 1);
							st.playSound(QuestState.SOUND_MIDDLE);
						}
						else if (cond == 3)
							htmltext = "30032-02.htm";
						break;
				}
				break;
			
			case STATE_COMPLETED:
				htmltext = getAlreadyCompletedMsg();
				break;
		}
		
		return htmltext;
	}
	
	@Override
	public String onKill(L2Npc npc, L2PcInstance player, boolean isPet)
	{
		QuestState st = checkPlayerCondition(player, npc, "cond", "1");
		if (st == null)
			return null;
		
		if (Rnd.get(100) < 20)
		{
			st.set("cond", "2");
			st.giveItems(POISON_SAC, 1);
			st.playSound(QuestState.SOUND_ITEMGET);
		}
		
		return null;
	}
	
	public static void main(String[] args)
	{
		new Q151_CureForFeverDisease(151, qn, "Cure for Fever Disease");
	}
}