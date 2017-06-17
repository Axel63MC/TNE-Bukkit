package com.github.tnerevival.commands.money;

import com.github.tnerevival.TNE;
import com.github.tnerevival.account.Account;
import com.github.tnerevival.account.IDFinder;
import com.github.tnerevival.commands.TNECommand;
import com.github.tnerevival.core.Message;
import com.github.tnerevival.core.currency.Currency;
import com.github.tnerevival.core.currency.CurrencyFormatter;
import com.github.tnerevival.core.transaction.TransactionType;
import com.github.tnerevival.utils.AccountUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.math.BigDecimal;

/**
 * Created by Daniel on 10/12/2016.
 */
public class MoneySetCommand extends TNECommand {

  public MoneySetCommand(TNE plugin) {
    super(plugin);
  }

  @Override
  public String getName() {
    return "set";
  }

  @Override
  public String[] getAliases() {
    return new String[0];
  }

  @Override
  public String getNode() {
    return "tne.money.set";
  }

  @Override
  public boolean console() {
    return true;
  }

  @Override
  public boolean execute(CommandSender sender, String command, String[] arguments) {
    if(arguments.length >= 2) {
      String world = (arguments.length == 3)? getWorld(sender, arguments[2]) : getWorld(sender);
      world = IDFinder.getBalanceShareWorld(world);
      String currencyName = (arguments.length >= 4)? arguments[3] : TNE.instance().manager.currencyManager.get(world).getName();
      Account acc = AccountUtils.getAccount(IDFinder.getID(arguments[0]));
      if(acc.getBalances().containsKey(world + ":" + currencyName)) {
        Message m = new Message("Messages.Money.NoCurrency");
        m.addVariable("$currency", currencyName);
        m.addVariable("$world", world);
        m.translate(world, sender);
        return false;
      }

      Currency currency = getCurrency(world, currencyName);
      String parsed = CurrencyFormatter.parseAmount(currency, world, arguments[1]);
      if(parsed.contains("Messages")) {
        Message max = new Message(parsed);
        max.addVariable("$currency", currency.getName());
        max.addVariable("$world", world);
        max.addVariable("$player", getPlayer(sender).getDisplayName());
        max.translate(getWorld(sender), sender);
        return false;
      }

      BigDecimal value = new BigDecimal(parsed);
      if(value.compareTo(BigDecimal.ZERO) < 0) {
        new Message("Messages.Money.Negative").translate(world, sender);
        return false;
      }
      if(value.compareTo(currency.getMaxBalance()) > 0) {
        Message exceeds = new Message("Messages.Money.ExceedsOtherPlayerMaximum");
        exceeds.addVariable("$max", CurrencyFormatter.format(world, currencyName, currency.getMaxBalance()));
        exceeds.addVariable("$player", arguments[0]);
        exceeds.translate(world, sender);
        return false;
      }

      if(arguments[0].equalsIgnoreCase(TNE.instance().api().getString("Core.Server.Name"))
          && !sender.hasPermission("tne.server.set")) {
        new Message("Messages.General.NoPerm").translate(world, sender);
        return false;
      }

      if(IDFinder.getID(arguments[0]) != null) {
        String id = (sender instanceof Player)? IDFinder.getID(getPlayer(sender)).toString() : null;

        AccountUtils.transaction(IDFinder.getID(arguments[0]).toString(), id, value, currency, TransactionType.MONEY_SET, world);
        Message set = new Message("Messages.Money.Set");
        set.addVariable("$amount",  CurrencyFormatter.format(world, value));
        set.addVariable("$player", arguments[0]);
        set.translate(world, sender);
        return true;
      }
    } else {
      help(sender);
    }
    return false;
  }



  @Override
  public String getHelp() {
    return "/money set <player> <amount> [world] [currency] - Set <player>'s balance to <amount>.";
  }
}
