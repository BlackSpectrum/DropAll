package io.github.mats391.dropall;

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

	private void dropItemNaturally( final Entity e, final ItemStack i ) {
		if ( i != null && !i.getType().equals( Material.AIR ) )
			e.getLocation().getWorld().dropItemNaturally( e.getLocation(), i );
	}

	private void dropItemNaturally( final Entity e, final MaterialData m ) {
		this.dropItemNaturally( e, m.toItemStack( 1 ) );
	}

	@Override
	public void onEnable() {
		final PluginManager pm = this.getServer().getPluginManager();
		pm.registerEvents( this, this );
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onEnderManDeath( final EntityDeathEvent event ) {
		if ( !( event.getEntity() instanceof Enderman ) )
			return;

		final Enderman e = (Enderman) event.getEntity();

		this.dropItemNaturally( e, e.getCarriedMaterial() );
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerDeath( final PlayerDeathEvent event ) {
		final Player player = event.getEntity();
		final Inventory enderChest = player.getEnderChest();

		for ( final ItemStack item : enderChest.getContents() )
			this.dropItemNaturally( player, item );

		enderChest.clear();
	}
}