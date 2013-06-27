package me.WolfCFR.BanItem;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

public class BlockListener implements Listener {
	public static Main plugin;

	public BlockListener(Main instance) {
		plugin = instance;
	}

	@EventHandler
	private void onPlayerPlacement(BlockPlaceEvent e) {
		if (plugin.getConfig().getBoolean("Prevent.Place")) {
			Player player = e.getPlayer();
			Integer id = e.getPlayer().getItemInHand().getType().getId();
			String ban = plugin.getConfig().getString("Ban Message");
			String con = plugin.getConfig().getString("Confiscate Message");
			Byte materialData = player.getItemInHand().getData().getData();
			if (player.hasPermission((new StringBuilder("BanItem.place."))
					.append(id).append(":").append(materialData).toString())
					|| player.isOp()
					|| player
							.hasPermission((new StringBuilder("BanItem.place."))
									.append(id).append(":*").toString())
					|| player.hasPermission((new StringBuilder(
							"BanItem.bypass.")).append(id).append(":*")
							.toString()) || player.hasPermission("banitem.*")) {
			} else {
				if (plugin.ac.contains(id + ":" + materialData)
						|| plugin.place.contains(id + ":" + materialData)
						|| plugin.ac.contains(id + ":*")
						|| plugin.place.contains(id + ":*")) {
					if (plugin.getConfig().getBoolean("Prevent.Confiscate")) {
						e.getItemInHand().setType(Material.AIR);
						e.getPlayer().getItemInHand().setType(Material.AIR);
						e.getPlayer().setItemInHand(
								new ItemStack(Material.AIR, 1));
						player.sendMessage(ChatColor.RED + "[" + ChatColor.GRAY
								+ "BanItem" + ChatColor.RED + "] "
								+ ChatColor.DARK_RED + con + ChatColor.YELLOW
								+ " [" + id + ":" + materialData + "]");
						e.setCancelled(true);
						e.setBuild(false);
					} else {
						player.sendMessage(ChatColor.RED + "[" + ChatColor.GRAY
								+ "BanItem" + ChatColor.RED + "] "
								+ ChatColor.DARK_RED + ban + ChatColor.YELLOW
								+ " [" + id + ":" + materialData + "]");
						e.setCancelled(true);
						e.setBuild(false);
					}
				} else {
				}
			}
		} else {
		}
	}

	@EventHandler
	private void onBlockBreak(BlockBreakEvent e) {
		if (plugin.getConfig().getBoolean("Prevent.Break")) {
			Player player = e.getPlayer();
			Integer id = e.getBlock().getType().getId();
			String ban = plugin.getConfig().getString("Ban Message");
			Byte materialData = e.getBlock().getData();
			if (player.hasPermission((new StringBuilder("banitem.break."))
					.append(id).append(":").append(materialData).toString())
					|| player.isOp()
					|| player
							.hasPermission((new StringBuilder("banitem.break."))
									.append(id).append(":*").toString())
					|| player.hasPermission((new StringBuilder(
							"banitem.bypass.")).append(id).append(":*")
							.toString()) || player.hasPermission("banitem.*")) {
			} else {
				if (plugin.ac.contains(id + ":" + materialData)
						|| plugin.br.contains(id + ":" + materialData)) {
					e.setCancelled(true);
					e.isCancelled();
					player.sendMessage(ChatColor.RED + "[" + ChatColor.GRAY
							+ "BanItem" + ChatColor.RED + "] "
							+ ChatColor.DARK_RED + ban);
				} else {
				}
			}
		} else {
		}
	}
}