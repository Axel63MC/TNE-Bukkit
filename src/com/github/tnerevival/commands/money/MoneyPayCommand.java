package com.github.tnerevival.commands.money;

import com.github.tnerevival.TNE;
import com.github.tnerevival.account.IDFinder;
import com.github.tnerevival.commands.TNECommand;
import com.github.tnerevival.core.Message;
import com.github.tnerevival.core.currency.Currency;
import com.github.tnerevival.core.currency.CurrencyFormatter;
import com.github.tnerevival.core.transaction.TransactionType;
import com.github.tnerevival.utils.AccountUtils;
import com.github.tnerevival.utils.MISCUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.math.BigDecimal;

public class MoneyPayCommand extends TNECommand {

  public MoneyPayCommand(TNE plugin) {
    super(plugin);
  }

  @Override
  public String getName() {
    return "pay";
  }

  @Override
  public String[] getAliases() {
    return new String[0];
  }

  @Override
  public String getNode() {
    return "tne.money.pay";
  }

  @Override
  public boolean console() {
    return false;
  }

  @Override
  public boolean execute(CommandSender sender, String command, String[] arguments) {
    Player player = getPlayer(sender);
    String world = IDFinder.findRealWorld(getPlayer(sender));
    world = IDFinder.findBalanceWorld(world);
    if(arguments.length >= 2) {
      String currencyName = (arguments.length >= 3)? arguments[2] : TNE.instance().manager.currencyManager.get(world).getName();
      Currency currency = getCurrency(world, currencyName);

      if(!TNE.instance().manager.currencyManager.contains(world, currencyName)) {
        Message m = new Message("Messages.Money.NoCurrency");
        m.addVariable("$currency", currencyName);
        m.addVariable("$world", world);
        m.translate(world, sender);
        return false;
      }

      String parsed = CurrencyFormatter.parseAmount(currency, world, arguments[1]);
      if(parsed.contains("Messages")) {
        Message max = new Message(parsed);
        max.addVariable("$currency", currency.getName());
        max.addVariable("$world", world);
        max.addVariable("$player", getPlayer(sender).getDisplayName());
        max.translate(IDFinder.findRealWorld(getPlayer(sender)), sender);
        return false;
      }

      BigDecimal value = new BigDecimal(parsed);
      if(value.compareTo(BigDecimal.ZERO) < 0) {
        new Message("Messages.Money.Negative").translate(world, sender);
        return false;
      }

      if(getPlayer(sender, arguments[0]) != null && IDFinder.getID(player).equals(IDFinder.getID(getPlayer(sender, arguments[0])))) {
        new Message("Messages.Money.SelfPay").translate(IDFinder.findRealWorld(player), player);
        return false;
      }

      if(arguments[0].equalsIgnoreCase(TNE.instance().api().getString("Core.Server.Name"))
          && !sender.hasPermission("tne.server.pay")) {
        new Message("Messages.General.NoPerm").translate(world, sender);
        return false;
      }

      if(AccountUtils.transaction(IDFinder.getID(player).toString(), null, value, TransactionType.MONEY_INQUIRY, IDFinder.findRealWorld(player))) {
        MISCUtils.debug("Player has funds");
        if(getPlayer(sender, arguments[0]) != null) {
          MISCUtils.debug("Player not null");

          BigDecimal comparison = AccountUtils.getFunds(IDFinder.getID(arguments[0]), world, currencyName);
          if(comparison.add(value).compareTo(currency.getMaxBalance()) > 0) {
            Message exceeds = new Message("Messages.Money.ExceedsOtherPlayerMaximum");
            exceeds.addVariable("$max", CurrencyFormatter.format(world, currencyName, currency.getMaxBalance()));
            exceeds.addVariable("$player", arguments[0]);
            exceeds.translate(world, sender);
            return false;
          }

          boolean transaction = AccountUtils.transaction(IDFinder.getID(player).toString(), IDFinder.getID(getPlayer(sender, arguments[0])).toString(), value, TransactionType.MONEY_PAY, IDFinder.findRealWorld(player));
          MISCUtils.debug("" + transaction);
          if(transaction) {
            MISCUtils.debug("Paid player");
            Message paid = new Message("Messages.Money.Paid");
            paid.addVariable("$amount", CurrencyFormatter.format(player.getWorld().getName(), value));
            paid.addVariable("$player", arguments[0]);
            paid.translate(IDFinder.findRealWorld(player), player);

            Message received = new Message("Messages.Money.Received");
            MISCUtils.debug(received.grab(IDFinder.findRealWorld(getPlayer(sender, arguments[0])), getPlayer(sender, arguments[0])));
            received.addVariable("$amount", CurrencyFormatter.format(player.getWorld().getName(), value));
            received.addVariable("$from", player.getName());
            received.translate(IDFinder.findRealWorld(getPlayer(sender, arguments[0])), getPlayer(sender, arguments[0]));
            return true;
          }
        }
      } else {
        Message insufficient = new Message("Messages.Money.Insufficient");
        insufficient.addVariable("$amount", CurrencyFormatter.format(player.getWorld().getName(), CurrencyFormatter.translateBigDecimal(arguments[1], IDFinder.findRealWorld(getPlayer(sender)))));
        insufficient.translate(IDFinder.findRealWorld(player), player);
        return false;
      }
    }
    help(sender);
    return false;
  }

  @Override
  public String getHelp() {
    return "/money pay <player> <amount> [currency] - pay a player money from your balance";
  }

}