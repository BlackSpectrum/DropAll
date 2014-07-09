package main.java.dropall;

import org.bukkit.Material;
import org.bukkit.entity.Enderman;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class DropAll extends JavaPlugin implements Listener
{

	@Override
	public void onEnable()
	{
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(this, this);
	}
	
	@EventHandler(priority=EventPriority.NORMAL)
	public void onPlayerDeath(PlayerDeathEvent event)
	{
		Player player = event.getEntity();
		Inventory enderChest = player.getEnderChest();
		
		for (ItemStack item : enderChest.getContents()) 
			dropItemNaturally(player, item);
	
		enderChest.clear();
	}
	
	
	@EventHandler(priority=EventPriority.NORMAL)
	public void onEnderManDeath(EntityDeathEvent event)
	{
		if(!(event.getEntity() instanceof Enderman))
			return;
		
		Enderman e = (Enderman)event.getEntity();
		
		dropItemNaturally(e, e.getCarriedMaterial());
	}
	
	
	private void dropItemNaturally(Entity e, ItemStack i)
	{
		if(i != null && !i.getType().equals(Material.AIR))
			e.getLocation().getWorld().dropItemNaturally(e.getLocation(), i);
	}
	
	private void dropItemNaturally(Entity e, MaterialData m)
	{
		dropItemNaturally(e, m.toItemStack(1));		
	}
}