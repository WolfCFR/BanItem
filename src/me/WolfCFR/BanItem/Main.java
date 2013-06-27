package me.WolfCFR.BanItem;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener {
	public final Logger log = Logger.getLogger("Minecraft");
	public final PlayerListener pl = new PlayerListener(this);
	public final BlockListener bl = new BlockListener(this);
	public List<String> ac = new ArrayList<String>();
	public List<String> craft = new ArrayList<String>();
	public List<String> place = new ArrayList<String>();
	public List<String> pickup = new ArrayList<String>();
	public List<String> interact = new ArrayList<String>();
	public List<String> click = new ArrayList<String>();
	public List<String> br = new ArrayList<String>();
	public final String banitem = ChatColor.RED + "[" + ChatColor.GRAY
			+ "BanItem" + ChatColor.RED + "] ";

	@Override
	public void onEnable() {
		PluginDescriptionFile pdfFile = this.getDescription();
		PluginManager pm = getServer().getPluginManager();
		log.info(pdfFile.getName() + " is now Enabled!");
		pm.registerEvents(this.pl, this);
		pm.registerEvents(this.bl, this);
		this.getConfig().options().copyDefaults(true);
		ac = this.getConfig().getStringList("Blacklist");
		craft = this.getConfig().getStringList("Blacklist Crafting");
		place = this.getConfig().getStringList("Blacklist Placement");
		br = this.getConfig().getStringList("Blacklist Break");
		pickup = this.getConfig().getStringList("Blacklist Pickup");
		interact = this.getConfig().getStringList("Blacklist Interaction");
		click = this.getConfig().getStringList("Blacklist Click");
		this.getConfig().getBoolean("Confiscate");
		this.getConfig().getString("Ban Message");
		this.getConfig().getString("Confiscate Message");
		this.getConfig().getBoolean("Prevent.Pickup");
		this.getConfig().getBoolean("Prevent.Break");
		this.getConfig().getBoolean("Prevent.Interaction");
		this.getConfig().getBoolean("Prevent.Craft");
		this.getConfig().getBoolean("Prevent.Place");
		this.getConfig().getBoolean("Prevent.Confiscate");
		this.getConfig().getBoolean("Prevent.Click");
	}

	public void onDisable() {
		PluginDescriptionFile pdfFile = this.getDescription();
		log.info(pdfFile.getName() + " is now Disabled!");
		this.getConfig().get("Blacklist", ac);
		this.getConfig().get("Blacklist Crafting", craft);
		this.getConfig().get("Blacklist Placement", place);
		this.getConfig().get("Blacklist Break", br);
		this.getConfig().get("Blacklist Pickup", pickup);
		this.getConfig().get("Blacklist Interaction", interact);
		this.getConfig().get("Blacklist Click", click);
		this.getConfig().set("Blacklist Click", click);
		this.getConfig().get("Confiscate");
		this.getConfig().get("Ban Message");
		this.saveConfig();
	}

	public boolean onCommand(CommandSender sender, Command cmd,
			String commandLabel, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			if (cmd.getName().equalsIgnoreCase("BanItem")) {
				if (args.length == 0) {
					if (player.hasPermission("BanItem.help") || player.isOp()
							|| player.hasPermission("BanItem.*")) {
						player.sendMessage(banitem + ChatColor.RED
								+ " Please use /BanItem help");
					} else {
						player.sendMessage(banitem
								+ ChatColor.DARK_RED
								+ "You do not have permission to use this command");
					}

				}
				if (args.length == 1) {
					Integer item = player.getItemInHand().getType().getId();
					Byte materialData = player.getItemInHand().getData()
							.getData();
					if (args[0].equalsIgnoreCase("add")) {
						if (player.hasPermission("BanItem.add")
								|| player.isOp()
								|| player.hasPermission("BanItem.*")) {
							if (ac.contains(item + ":" + materialData)) {
								player.sendMessage(banitem
										+ ChatColor.YELLOW
										+ "["
										+ item
										+ ":"
										+ materialData
										+ "]"
										+ ChatColor.GREEN
										+ " had already been Added to the item blacklist");
							} else {
								player.sendMessage(banitem
										+ ChatColor.YELLOW
										+ "["
										+ item
										+ ":"
										+ materialData
										+ "]"
										+ ChatColor.GREEN
										+ " has been Added to the item blacklist");
								ac.add(item + ":" + materialData);
								this.getConfig().set("Blacklist", ac);

								player.sendMessage(banitem + ChatColor.GREEN
										+ "Blacklisted Items"
										+ ChatColor.YELLOW + "~ "
										+ ChatColor.RED + ac + ChatColor.YELLOW
										+ " ~");
							}
						} else {
							player.sendMessage(banitem
									+ ChatColor.DARK_RED
									+ "You do not have permission to use this command");
						}
						return false;
					}
					if (args[0].equalsIgnoreCase("del")) {
						if (player.hasPermission("BanItem.del")
								|| player.isOp()
								|| player.hasPermission("BanItem.*")) {
							if (ac.contains(item + ":" + materialData)) {
								ac.remove(item + ":" + materialData);
								this.getConfig().set("Blacklist", ac);
								player.sendMessage(banitem
										+ ChatColor.YELLOW
										+ "["
										+ item
										+ ":"
										+ materialData
										+ "]"
										+ ChatColor.GREEN
										+ " has been Removed from the item blacklist");
								player.sendMessage(banitem + ChatColor.GREEN
										+ "Blacklisted Items"
										+ ChatColor.YELLOW + "~ "
										+ ChatColor.RED + ac + ChatColor.YELLOW
										+ " ~");
							} else {
								player.sendMessage(banitem + ChatColor.YELLOW
										+ "[" + item + ":" + materialData + "]"
										+ ChatColor.GREEN
										+ " is not in the item blacklist");
								player.sendMessage(banitem + ChatColor.GREEN
										+ "Blacklisted Items"
										+ ChatColor.YELLOW + "~ "
										+ ChatColor.RED + ac + ChatColor.YELLOW
										+ " ~");
							}
						} else {
							player.sendMessage(banitem
									+ ChatColor.DARK_RED
									+ "You do not have permission to use this command");
						}
						return false;
					}
					if (args[0].equalsIgnoreCase("help")) {
						if (player.hasPermission("BanItem.help")
								|| player.isOp()
								|| player.hasPermission("BanItem.*")) {
							player.sendMessage(ChatColor.LIGHT_PURPLE
									+ "----BanItem help (Admin) p(1/1)----");
							player.sendMessage(ChatColor.GOLD
									+ "use /BanItem add"
									+ ChatColor.GREEN
									+ " | "
									+ ChatColor.AQUA
									+ "hold ban item to ban them by using /BanItem add");
							player.sendMessage(ChatColor.GOLD
									+ "use /BanItem del"
									+ ChatColor.GREEN
									+ " | "
									+ ChatColor.AQUA
									+ "hold banned item to unban them by using /BanItem del");
							player.sendMessage(ChatColor.GOLD
									+ "use /BanItem list" + ChatColor.GREEN
									+ " | " + ChatColor.AQUA
									+ "Will show Blacklisted Items");
							player.sendMessage(ChatColor.GOLD
									+ "use /BanItem clear" + ChatColor.GREEN
									+ " | " + ChatColor.AQUA
									+ "Clear Blacklist");
							player.sendMessage(ChatColor.GOLD
									+ "use /BanItem toggle" + ChatColor.GREEN
									+ " | " + ChatColor.AQUA
									+ "toggle item confiscation");
							player.sendMessage(ChatColor.GOLD
									+ "use /BanItem config" + ChatColor.GREEN
									+ " | " + ChatColor.AQUA
									+ "Shows Config options.");
						} else {
							player.sendMessage(banitem
									+ ChatColor.DARK_RED
									+ "You do not have permission to use this command");
						}
						return false;
					}
					if (args[0].equalsIgnoreCase("list")) {
						if (player.hasPermission("BanItem.list")
								|| player.isOp()
								|| player.hasPermission("BanItem.*")) {
							player.sendMessage(banitem + ChatColor.GREEN
									+ "Blacklisted Items" + ChatColor.YELLOW
									+ ": " + ChatColor.RED + ac);
							player.sendMessage(banitem + ChatColor.GREEN
									+ "Crafting" + ChatColor.YELLOW + ": "
									+ ChatColor.RED + craft);
							player.sendMessage(banitem + ChatColor.GREEN
									+ "Pickup" + ChatColor.YELLOW + ": "
									+ ChatColor.RED + pickup);
							player.sendMessage(banitem + ChatColor.GREEN
									+ "Break" + ChatColor.YELLOW + ": "
									+ ChatColor.RED + br);
							player.sendMessage(banitem + ChatColor.GREEN
									+ "Placement" + ChatColor.YELLOW + ": "
									+ ChatColor.RED + place);
							player.sendMessage(banitem + ChatColor.GREEN
									+ "Interaction" + ChatColor.YELLOW + ": "
									+ ChatColor.RED + interact);
						} else {
							player.sendMessage(banitem
									+ ChatColor.DARK_RED
									+ "You do not have permission to use this command");
						}
						return false;
					}
					if (args[0].equalsIgnoreCase("clear")) {
						if (player.hasPermission("Banitem.clear")
								|| player.isOp()
								|| player.hasPermission("BanItem.*")) {
							ac.clear();
							player.sendMessage(banitem + ChatColor.GREEN
									+ "Cleared Blacklisted Items");
						} else {
							player.sendMessage(banitem
									+ ChatColor.DARK_RED
									+ "You do not have permission to use this command");
						}
						return false;
					}
					if (args[0].equalsIgnoreCase("reload")) {
						if (player.hasPermission("Banitem.reload")
								|| player.isOp()
								|| player.hasPermission("BanItem.*")) {
							this.reloadConfig();
							player.sendMessage(banitem + ChatColor.GREEN
									+ "Reload Complete");
						} else {
							player.sendMessage(banitem
									+ ChatColor.DARK_RED
									+ "You do not have permission to use this command");

						}
						return false;
					}
					if (args[0].equalsIgnoreCase("toggle")) {
						if (player.hasPermission("Banitem.toggle")
								|| player.isOp()
								|| player.hasPermission("BanItem.*")) {
							player.sendMessage(banitem
									+ ChatColor.RED
									+ "Wrong use of command! /banitem toggle [Params]");
							player.sendMessage(banitem
									+ ChatColor.GREEN
									+ "Params = Conf, Craft, Place, Pickup, Break, Click, Int");
						} else {
						}
						return false;
					}
					if (args[0].equalsIgnoreCase("config")) {
						if (player.hasPermission("Banitem.config")
								|| player.isOp()
								|| player.hasPermission("BanItem.*")) {
							player.sendMessage(banitem
									+ ChatColor.GREEN
									+ "Pickup: "
									+ this.getConfig().getBoolean(
											"Prevent.Pickup"));
							player.sendMessage(banitem
									+ ChatColor.GREEN
									+ "Craft: "
									+ this.getConfig().getBoolean(
											"Prevent.Craft"));
							player.sendMessage(banitem
									+ ChatColor.GREEN
									+ "Interaction: "
									+ this.getConfig().getBoolean(
											"Prevent.Interaction"));
							player.sendMessage(banitem
									+ ChatColor.GREEN
									+ "Break: "
									+ this.getConfig().getBoolean(
											"Prevent.Break"));
							player.sendMessage(banitem
									+ ChatColor.GREEN
									+ "Place: "
									+ this.getConfig().getBoolean(
											"Prevent.Place"));
							player.sendMessage(banitem
									+ ChatColor.GREEN
									+ "Confiscate: "
									+ this.getConfig().getBoolean(
											"Prevent.Confiscate"));
							player.sendMessage(banitem
									+ ChatColor.GREEN
									+ "Click: "
									+ this.getConfig().getBoolean(
											"Prevent.Click"));
						} else {
							player.sendMessage(banitem
									+ ChatColor.DARK_RED
									+ "You do not have permission to use this command");
						}
						return false;
					} else {
						player.sendMessage(banitem + ChatColor.RED
								+ "Wrong use of command! Type /banitem help!");
						return false;
					}
				}
				if (args.length == 2) {
					if (args[0].equalsIgnoreCase("toggle")) {
						if (args[1].equalsIgnoreCase("confiscate")
								|| args[1].equalsIgnoreCase("conf")) {
							if (player.hasPermission("Banitem.toggle")
									|| player.isOp()
									|| player.hasPermission("BanItem.*")) {
								if (this.getConfig().getBoolean(
										"Prevent.Confiscate")) {
									this.getConfig().set("Prevent.Confiscate",
											false);
									player.sendMessage(banitem
											+ ChatColor.GREEN
											+ "Confiscation Toggled off");
									return false;
								} else {
									this.getConfig().set("Prevent.Confiscate",
											true);
									player.sendMessage(banitem
											+ ChatColor.GREEN
											+ "Confiscation Toggled on");
									return false;
								}
							} else {
								player.sendMessage(banitem
										+ ChatColor.DARK_RED
										+ "You do not have permission to use this command");
								return false;
							}
						}
						if (args[1].equalsIgnoreCase("pickup")) {
							if (player.hasPermission("Banitem.toggle")
									|| player.isOp()
									|| player.hasPermission("BanItem.*")) {
								if (this.getConfig().getBoolean(
										"Prevent.Pickup")) {
									this.getConfig().set("Prevent.Pickup",
											false);
									player.sendMessage(banitem
											+ ChatColor.GREEN
											+ "Prevent Pickup Toggled off");
									return false;
								} else {
									this.getConfig()
											.set("Prevent.Pickup", true);
									player.sendMessage(banitem
											+ ChatColor.GREEN
											+ "Prevent Pickup Toggled on");
									return false;
								}
							} else {
								player.sendMessage(banitem
										+ ChatColor.DARK_RED
										+ "You do not have permission to use this command");
								return false;
							}
						}
						if (args[1].equalsIgnoreCase("break")) {
							if (player.hasPermission("Banitem.toggle")
									|| player.isOp()
									|| player.hasPermission("BanItem.*")) {
								if (this.getConfig()
										.getBoolean("Prevent.Break")) {
									this.getConfig()
											.set("Prevent.Break", false);
									player.sendMessage(banitem
											+ ChatColor.GREEN
											+ "Prevent Block Break Toggled off");
									return false;
								} else {
									this.getConfig().set("Prevent.Break", true);
									player.sendMessage(banitem
											+ ChatColor.GREEN
											+ "Prevent Block Break Toggled on");
									return false;
								}
							} else {
								player.sendMessage(banitem
										+ ChatColor.DARK_RED
										+ "You do not have permission to use this command");
								return false;
							}
						}
						if (args[1].equalsIgnoreCase("int")) {
							if (player.hasPermission("Banitem.toggle")
									|| player.isOp()
									|| player.hasPermission("BanItem.*")) {
								if (this.getConfig().getBoolean(
										"Prevent.Interaction")) {
									this.getConfig().set("Prevent.Interaction",
											false);
									player.sendMessage(banitem
											+ ChatColor.GREEN
											+ "Prevent Item Interaction Toggled off");
									return false;
								} else {
									this.getConfig().set("Prevent.Interaction",
											true);
									player.sendMessage(banitem
											+ ChatColor.GREEN
											+ "Prevent Item Interaction Toggled on");
									return false;
								}
							} else {
								player.sendMessage(banitem
										+ ChatColor.DARK_RED
										+ "You do not have permission to use this command");
								return false;
							}
						}
						if (args[1].equalsIgnoreCase("Place")) {
							if (player.hasPermission("Banitem.toggle")
									|| player.isOp()
									|| player.hasPermission("BanItem.*")) {
								if (this.getConfig()
										.getBoolean("Prevent.Place")) {
									this.getConfig()
											.set("Prevent.Place", false);
									player.sendMessage(banitem
											+ ChatColor.GREEN
											+ "Prevent Item Place Toggled off");
									return false;
								} else {
									this.getConfig().set("Prevent.Place", true);
									player.sendMessage(banitem
											+ ChatColor.GREEN
											+ "Prevent Item Place Toggled on");
									return false;
								}
							} else {
								player.sendMessage(banitem
										+ ChatColor.DARK_RED
										+ "You do not have permission to use this command");
								return false;
							}
						}
						if (args[1].equalsIgnoreCase("Click")) {
							if (player.hasPermission("Banitem.toggle")
									|| player.isOp()
									|| player.hasPermission("BanItem.*")) {
								if (this.getConfig()
										.getBoolean("Prevent.Click")) {
									this.getConfig()
											.set("Prevent.Click", false);
									player.sendMessage(banitem
											+ ChatColor.GREEN
											+ "Prevent Item Click Toggled off");
									return false;
								} else {
									this.getConfig().set("Prevent.Click", true);
									player.sendMessage(banitem
											+ ChatColor.GREEN
											+ "Prevent Item Click Toggled on");
									return false;
								}
							} else {
								player.sendMessage(banitem
										+ ChatColor.DARK_RED
										+ "You do not have permission to use this command");
								return false;
							}
						}
						if (args[1].equalsIgnoreCase("Craft")) {
							if (player.hasPermission("Banitem.toggle")
									|| player.isOp()
									|| player.hasPermission("BanItem.*")) {
								if (this.getConfig()
										.getBoolean("Prevent.Craft")) {
									this.getConfig()
											.set("Prevent.Craft", false);
									player.sendMessage(banitem
											+ ChatColor.GREEN
											+ "Prevent Item Craft Toggled off");
									return false;
								} else {
									this.getConfig().set("Prevent.Craft", true);
									player.sendMessage(banitem
											+ ChatColor.GREEN
											+ "Prevent Item Craft Toggled on");
									return false;
								}
							} else {
								player.sendMessage(banitem
										+ ChatColor.DARK_RED
										+ "You do not have permission to use this command");
								return false;
							}
						} else {
							player.sendMessage(banitem
									+ ChatColor.RED
									+ "Wrong use of command! /banitem toggle [Params]");
							player.sendMessage(banitem
									+ ChatColor.GREEN
									+ "Params = Conf, Craft, Place, Pickup, Break, Click, Int");
							return false;
						}
					}
				}
			} else {
				player.sendMessage(banitem + ChatColor.RED
						+ "Wrong use of command! Type /banitem help!");
				return false;
			}
		} else {
			if (args[0].equalsIgnoreCase("list")) {
				sender.sendMessage("[BanItem] Blacklisted Items : " + ac);
				return true;
			}
			if (args[0].equalsIgnoreCase("help")) {
				sender.sendMessage("----BanItem help (Console)----");
				sender.sendMessage("use /BanItem clear");
				sender.sendMessage("use /BanItem list");
				return true;
			}
			if (args[0].equalsIgnoreCase("clear")) {
				ac.clear();
				sender.sendMessage("[BanItem] Cleared Blacklist!");
				return true;
			}
			if (args[0].equalsIgnoreCase("reload")) {
				this.reloadConfig();
				sender.sendMessage("[BanItem] Reload complete!");
				return true;
			} else {
				sender.sendMessage("[BanItem] Wrong use of command! Type /banitem help!");
				return true;
			}
		}
		return false;
	}
}