package com.github.tnerevival.commands.bank;

import com.github.tnerevival.TNE;
import com.github.tnerevival.account.Account;
import com.github.tnerevival.account.Bank;
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
import java.util.UUID;

public class BankWithdrawCommand extends TNECommand {

  public BankWithdrawCommand(TNE plugin) {
    super(plugin);
  }

  @Override
  public String getName() {
    return "withdraw";
  }

  @Override
  public String[] getAliases() {
    return new String[0];
  }

  @Override
  public String getNode() {
    return "tne.bank.withdraw";
  }

  @Override
  public boolean console() {
    return false;
  }

  @Override
  public boolean execute(CommandSender sender, String command, String[] arguments) {
    Player player = getPlayer(sender);
    String world = (arguments.length >= 2)? arguments[1] : IDFinder.findRealWorld(getPlayer(sender));
    world = IDFinder.findBalanceWorld(world);
    String owner = (arguments.length >= 3)? arguments[2] : player.getName();
    String currencyName = (arguments.length >= 4)? getCurrency(world, arguments[3]).getName() : plugin.manager.currencyManager.get(world).getName();

    if(!TNE.instance().manager.currencyManager.contains(world, currencyName)) {
      Message m = new Message("Messages.Money.NoCurrency");
      m.addVariable("$currency", currencyName);
      m.addVariable("$world", world);
      m.translate(world, sender);
      return false;
    }

    Currency currency = getCurrency(world, currencyName);
    Account account = AccountUtils.getAccount(IDFinder.getID(owner));
    UUID id = IDFinder.getID(player);

    if(arguments.length < 1) {
      help(sender);
      return false;
    }

    String parsed = CurrencyFormatter.parseAmount(currency, world, arguments[0]);
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

    if(IDFinder.getID(owner) == null) {
      Message notFound = new Message("Messages.General.NoPlayer");
      notFound.addVariable("$player", owner);
      notFound.translate(IDFinder.findRealWorld(player), player);
      return false;
    }

    if(!account.hasBank(world) && !owner.equals(player.getName())) {
      Message none = new Message("Messages.Bank.None");
      none.addVariable("$amount",  CurrencyFormatter.format(IDFinder.findRealWorld(getPlayer(sender)), Bank.cost(IDFinder.findRealWorld(getPlayer(sender)), IDFinder.getID(player).toString())));
      none.translate(IDFinder.findRealWorld(getPlayer(sender)), player);
      return false;
    }

    if(!AccountUtils.getAccount(IDFinder.getID(owner)).hasBank(world) || !Bank.bankMember(IDFinder.getID(owner), IDFinder.getID(sender.getName())) || !world.equals(IDFinder.findRealWorld(getPlayer(sender))) && !TNE.instance().api().getBoolean("Core.Bank.MultiManage")) {
      new Message("Messages.General.NoPerm").translate(IDFinder.findRealWorld(player), player);
      return false;
    }

    BigDecimal comparison = AccountUtils.getFunds(id, world, currencyName);
    if(comparison.add(value).compareTo(currency.getMaxBalance()) > 0) {
      Message exceeds = new Message("Messages.Money.ExceedsPlayerMaximum");
      exceeds.addVariable("$max",  CurrencyFormatter.format(world, currencyName, currency.getMaxBalance()));
      exceeds.translate(IDFinder.findRealWorld(id), id);
      return false;
    }

    if(!AccountUtils.transaction(IDFinder.getID(owner).toString(), id.toString(), value, currency, TransactionType.BANK_WITHDRAWAL, IDFinder.findRealWorld(id))) {
      Message overdraw = new Message("Messages.Bank.Overdraw");
      overdraw.addVariable("$amount",  CurrencyFormatter.format(world, currencyName, value));
      overdraw.addVariable("$name",  owner);
      overdraw.translate(IDFinder.findRealWorld(id), id);
      return false;
    }

    Message withdrawn = new Message("Messages.Bank.Withdraw");
    withdrawn.addVariable("$amount",  CurrencyFormatter.format(world, currencyName, value));
    withdrawn.addVariable("$name",  owner);
    withdrawn.translate(IDFinder.findRealWorld(id), id);
    return true;
  }

  @Override
  public String getHelp() {
    return "/bank withdraw <amount> [world] [owner] [currency] - Withdraw <amount> from [owner]'s bank for world [world]. Defaults to your bank.";
  }
}