package th.ac.ku.atm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import th.ac.ku.atm.model.BankAccount;
import th.ac.ku.atm.model.Customer;
import th.ac.ku.atm.service.BankAccountService;

@Controller
@RequestMapping("/bankaccount")
public class BankAccountController {

    private BankAccountService bankAccountService;
    public BankAccountController(BankAccountService bankAccountService){
        this.bankAccountService = bankAccountService;
    }
    @GetMapping
    public String getBankAccountPage(Model model){

        //step 1. update model for template
        model.addAttribute("bankaccounts",bankAccountService.getBankAccount());

        //step 2. choose HTML template
        return "bankaccount";
    }

    @PostMapping
    public String registerCustomer(@ModelAttribute BankAccount bankAccount, Model model){
        bankAccountService.createBankAccount(bankAccount);
        model.addAttribute("allCustomers",bankAccountService.getBankAccount());
        return "redirect:bankaccount";
    }
}
