package me.WolfCFR.BanItem;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerListener implements Listener {
	public static Main plugin;

	public PlayerListener(Main instance) {
		plugin = instance;
	}

	@EventHandler
	private void onPlayerInteraction(PlayerInteractEvent e) {
		if (plugin.getConfig().getBoolean("Prevent.Interaction")) {
			Player player = e.getPlayer();
			Byte materialData = player.getItemInHand().getData().getData();
			String ban = plugin.getConfig().getString("Ban Message");
			String con = plugin.getConfig().getString("Confiscate Message");
			Integer id = e.getPlayer().getItemInHand().getType().getId();
			if (player.hasPermission((new StringBuilder("banitem.int."))
					.append(id).append(":").append(materialData).toString())
					|| player.isOp()
					|| player.hasPermission((new StringBuilder("banitem.int."))
							.append(id).append(":*").toString())
					|| player.hasPermission((new StringBuilder(
							"banitem.bypass.")).append(id).append(":*")
							.toString())
					|| player.hasPermission((new StringBuilder(
							"banitem.bypass.")).append(id).append(":")
							.append(materialData).toString())
					|| player.hasPermission("banitem.*")) {

			} else {
				if (plugin.ac.contains(id + ":" + materialData)
						|| plugin.interact.contains(id + ":" + materialData)
						|| plugin.ac.contains(id + ":*")
						|| plugin.interact.contains(id + ":*")) {
					if (plugin.getConfig().getBoolean("Prevent.Confiscate")) {
						e.getPlayer().getItemInHand().setType(Material.AIR);
						e.getItem().setType(Material.AIR);
						e.getPlayer().setItemInHand(
								new ItemStack(Material.AIR, 1));
						player.sendMessage(ChatColor.RED + "[" + ChatColor.GRAY
								+ "BanItem" + ChatColor.RED + "] "
								+ ChatColor.DARK_RED + con + ChatColor.YELLOW
								+ " [" + id + ":" + materialData + "]");
						e.setCancelled(true);
					} else {
						player.sendMessage(ChatColor.RED + "[" + ChatColor.GRAY
								+ "BanItem" + ChatColor.RED + "] "
								+ ChatColor.DARK_RED + ban + ChatColor.YELLOW
								+ " [" + id + ":" + materialData + "]");
						e.setCancelled(true);
					}
				} else {
				}
			}
		} else {
		}
	}

	@EventHandler
	private void onPickup(PlayerPickupItemEvent e) {
		if (plugin.getConfig().getBoolean("Prevent.Pickup")) {
			Player player = e.getPlayer();
			ItemStack item = e.getItem().getItemStack();
			Integer id = item.getType().getId();
			Byte materialData = item.getData().getData();
			if (player.hasPermission((new StringBuilder("banitem.pickup."))
					.append(id).append(":").append(materialData).toString())
					|| player.isOp()
					|| player.hasPermission((new StringBuilder(
							"banitem.pickup.")).append(id).append(":*")
							.toString())
					|| player.hasPermission((new StringBuilder(
							"banitem.bypass.")).append(id).append(":*")
							.toString())
					|| player.hasPermission((new StringBuilder(
							"banitem.bypass.")).append(id).append(":")
							.append(materialData).toString())
					|| player.hasPermission("banitem.*")) {

			} else {
				if (plugin.ac.contains(id + ":" + materialData)
						|| plugin.pickup.contains(id + ":" + materialData)
						|| plugin.ac.contains(item + ":*")
						|| plugin.pickup.contains(item + ":*")) {
					e.setCancelled(true);
				} else {

				}
			}
		} else {
		}
	}

	@EventHandler
	private void onCrafted(CraftItemEvent e) {
		if (plugin.getConfig().getBoolean("Prevent.Craft")) {
			Player player = (Player) e.getWhoClicked();
			ItemStack item = e.getRecipe().getResult();
			Integer id = item.getType().getId();
			String ban = plugin.getConfig().getString("Ban Message");
			Byte materialData = item.getData().getData();
			if (player.hasPermission((new StringBuilder("banitem.craft."))
					.append(id).append(":").append(materialData).toString())
					|| player.isOp()
					|| player
							.hasPermission((new StringBuilder("banitem.craft."))
									.append(id).append(":*").toString())
					|| player.hasPermission((new StringBuilder(
							"banitem.bypass.")).append(id).append(":*")
							.toString())
					|| player.hasPermission((new StringBuilder(
							"banitem.bypass.")).append(id).append(":")
							.append(materialData).toString())
					|| player.hasPermission("banitem.*")) {
			} else {
				if (plugin.ac.contains(id + ":" + materialData)
						|| plugin.craft.contains(id + ":" + materialData)
						|| plugin.ac.contains(item + ":*")
						|| plugin.craft.contains(item + ":*")) {
					player.sendMessage(ChatColor.RED + "[" + ChatColor.GRAY
							+ "BanItem" + ChatColor.RED + "] "
							+ ChatColor.DARK_RED + ban);
					e.setCancelled(true);
				} else {
				}
			}
		} else {
		}
	}

	@EventHandler
	private void onInventoryClick(InventoryClickEvent e) {
		if (e instanceof Player) {
		} else {
			if (plugin.getConfig().getBoolean("Prevent.Click")) {
				Player player = (Player) e.getWhoClicked();
				ItemStack item = e.getCurrentItem();
				if (item == null) {
					return;
				}
				int id = item.getType().getId();
				String con = plugin.getConfig().getString("Confiscate Message");
				Byte materialData = item.getData().getData();
				String ban = plugin.getConfig().getString("Ban Message");
				if (player
						.hasPermission((new StringBuilder("banitem.Click."))
								.append(id).append(":").append(materialData)
								.toString())
						|| player.isOp()
						|| player.hasPermission((new StringBuilder(
								"banitem.Click.")).append(id).append(":*")
								.toString())
						|| player.hasPermission((new StringBuilder(
								"banitem.bypass.")).append(id).append(":*")
								.toString())
						|| player.hasPermission((new StringBuilder(
								"banitem.bypass.")).append(id).append(":")
								.append(materialData).toString())
						|| player.hasPermission("banitem.*")) {
				} else {
					if (plugin.ac.contains(id + ":" + materialData)
							|| plugin.click.contains(id + ":" + materialData)
							|| plugin.ac.contains(id + ":*")
							|| plugin.click.contains(id + ":*")) {
						if (plugin.getConfig().getBoolean("Prevent.Confiscate")) {
							e.setCurrentItem(new ItemStack(Material.AIR, 1));
							e.setCancelled(true);
							player.sendMessage(ChatColor.RED + "["
									+ ChatColor.GRAY + "BanItem"
									+ ChatColor.RED + "] " + ChatColor.DARK_RED
									+ con + ChatColor.YELLOW + " [" + id + ":"
									+ materialData + "]");
						} else {
							player.sendMessage(ChatColor.RED + "["
									+ ChatColor.GRAY + "BanItem"
									+ ChatColor.RED + "] " + ChatColor.DARK_RED
									+ ban + ChatColor.YELLOW + " [" + id + ":"
									+ materialData + "]");
							e.setCancelled(true);
						}
					} else {
					}
				}
			} else {
			}
		}
	}
}
