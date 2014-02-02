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
package quests.Q017_LightAndDarkness;

import net.sf.l2j.gameserver.model.actor.L2Npc;
import net.sf.l2j.gameserver.model.actor.instance.L2PcInstance;
import net.sf.l2j.gameserver.model.quest.Quest;
import net.sf.l2j.gameserver.model.quest.QuestState;

public class Q017_LightAndDarkness extends Quest
{
	private static final String qn = "Q017_LightAndDarkness";
	
	// Items
	private static final int BloodOfSaint = 7168;
	
	// NPCs
	private static final int Hierarch = 31517;
	private static final int AltarOfSaints1 = 31508;
	private static final int AltarOfSaints2 = 31509;
	private static final int AltarOfSaints3 = 31510;
	private static final int AltarOfSaints4 = 31511;
	
	public Q017_LightAndDarkness(int questId, String name, String descr)
	{
		super(questId, name, descr);
		
		questItemIds = new int[]
		{
			BloodOfSaint
		};
		
		addStartNpc(Hierarch);
		addTalkId(Hierarch, AltarOfSaints1, AltarOfSaints2, AltarOfSaints3, AltarOfSaints4);
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		String htmltext = event;
		QuestState st = player.getQuestState(qn);
		if (st == null)
			return htmltext;
		
		if (event.equalsIgnoreCase("31517-04.htm"))
		{
			st.set("cond", "1");
			st.setState(STATE_STARTED);
			st.giveItems(BloodOfSaint, 4);
			st.playSound(QuestState.SOUND_ACCEPT);
		}
		else if (event.equalsIgnoreCase("31508-02.htm"))
		{
			if (st.hasQuestItems(BloodOfSaint))
			{
				st.set("cond", "2");
				st.takeItems(BloodOfSaint, 1);
				st.playSound(QuestState.SOUND_MIDDLE);
			}
			else
				htmltext = "31508-03.htm";
		}
		else if (event.equalsIgnoreCase("31509-02.htm"))
		{
			if (st.hasQuestItems(BloodOfSaint))
			{
				st.set("cond", "3");
				st.takeItems(BloodOfSaint, 1);
				st.playSound(QuestState.SOUND_MIDDLE);
			}
			else
				htmltext = "31509-03.htm";
		}
		else if (event.equalsIgnoreCase("31510-02.htm"))
		{
			if (st.hasQuestItems(BloodOfSaint))
			{
				st.set("cond", "4");
				st.takeItems(BloodOfSaint, 1);
				st.playSound(QuestState.SOUND_MIDDLE);
			}
			else
				htmltext = "31510-03.htm";
		}
		else if (event.equalsIgnoreCase("31511-02.htm"))
		{
			if (st.hasQuestItems(BloodOfSaint))
			{
				st.set("cond", "5");
				st.takeItems(BloodOfSaint, 1);
				st.playSound(QuestState.SOUND_MIDDLE);
			}
			else
				htmltext = "31511-03.htm";
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
				htmltext = (player.getLevel() < 61) ? "31517-03.htm" : "31517-01.htm";
				break;
			
			case STATE_STARTED:
				int cond = st.getInt("cond");
				switch (npc.getNpcId())
				{
					case Hierarch:
						if (cond == 5)
						{
							htmltext = "31517-07.htm";
							st.rewardExpAndSp(105527, 0);
							st.playSound(QuestState.SOUND_FINISH);
							st.exitQuest(false);
						}
						else
						{
							if (st.hasQuestItems(BloodOfSaint))
								htmltext = "31517-05.htm";
							else
							{
								htmltext = "31517-06.htm";
								st.exitQuest(true);
							}
						}
						break;
					
					case AltarOfSaints1:
						if (cond == 1)
							htmltext = "31508-01.htm";
						else if (cond >= 2)
							htmltext = "31508-04.htm";
						break;
					
					case AltarOfSaints2:
						if (cond == 2)
							htmltext = "31509-01.htm";
						else if (cond > 2)
							htmltext = "31509-04.htm";
						break;
					
					case AltarOfSaints3:
						if (cond == 3)
							htmltext = "31510-01.htm";
						else if (cond > 3)
							htmltext = "31510-04.htm";
						break;
					
					case AltarOfSaints4:
						if (cond == 4)
							htmltext = "31511-01.htm";
						else if (cond > 4)
							htmltext = "31511-04.htm";
						break;
				}
				break;
			
			case STATE_COMPLETED:
				htmltext = getAlreadyCompletedMsg();
				break;
		}
		
		return htmltext;
	}
	
	public static void main(String[] args)
	{
		new Q017_LightAndDarkness(17, qn, "Light and Darkness");
	}
}