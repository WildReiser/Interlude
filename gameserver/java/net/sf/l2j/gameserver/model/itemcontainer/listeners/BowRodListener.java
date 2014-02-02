/*
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>.
 */
package net.sf.l2j.gameserver.model.itemcontainer.listeners;

import net.sf.l2j.gameserver.model.L2ItemInstance;
import net.sf.l2j.gameserver.model.actor.L2Playable;
import net.sf.l2j.gameserver.model.itemcontainer.Inventory;
import net.sf.l2j.gameserver.templates.item.L2WeaponType;

public class BowRodListener implements OnEquipListener
{
	private static BowRodListener instance = new BowRodListener();
	
	public static BowRodListener getInstance()
	{
		return instance;
	}
	
	@Override
	public void onEquip(int slot, L2ItemInstance item, L2Playable actor)
	{
		if (slot != Inventory.PAPERDOLL_RHAND)
			return;
		
		if (item.getItemType() == L2WeaponType.BOW)
		{
			final L2ItemInstance arrow = actor.getInventory().findArrowForBow(item.getItem());
			if (arrow != null)
				actor.getInventory().setPaperdollItem(Inventory.PAPERDOLL_LHAND, arrow);
		}
	}
	
	@Override
	public void onUnequip(int slot, L2ItemInstance item, L2Playable actor)
	{
		if (slot != Inventory.PAPERDOLL_RHAND)
			return;
		
		if (item.getItemType() == L2WeaponType.BOW)
		{
			final L2ItemInstance arrow = actor.getInventory().getPaperdollItem(Inventory.PAPERDOLL_LHAND);
			if (arrow != null)
				actor.getInventory().setPaperdollItem(Inventory.PAPERDOLL_LHAND, null);
		}
		else if (item.getItemType() == L2WeaponType.FISHINGROD)
		{
			final L2ItemInstance lure = actor.getInventory().getPaperdollItem(Inventory.PAPERDOLL_LHAND);
			if (lure != null)
				actor.getInventory().setPaperdollItem(Inventory.PAPERDOLL_LHAND, null);
		}
	}
}