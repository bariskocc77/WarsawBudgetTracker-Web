package com.warsaw.budget_tracker;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;

@Controller
public class BudgetController {

    private final ExpenseManager manager;
    private final CurrencyService currencyService;

    public BudgetController(ExpenseManager manager, CurrencyService currencyService) {
        this.manager = manager;
        this.currencyService = currencyService;
    }

    @GetMapping("/")
    public String showHomePage(Model model) {
        // Listeyi gönder
        model.addAttribute("expenses", manager.getAllExpenses());

        // YENİ: Toplam tutarları gönder
        model.addAttribute("totalPln", manager.getTotalPln());
        model.addAttribute("totalTry", manager.getTotalTry());

        return "home";
    }

    @PostMapping("/add")
    public String addNewExpense(@RequestParam String desc,
                                @RequestParam double amount,
                                @RequestParam String category,
                                RedirectAttributes redirectAttributes) {

        // Kur hesapla
        double rate = currencyService.getTryRate();
        double tryAmount = 0.0;
        if (rate > 0) {
            tryAmount = amount / rate;
        }

        // Kaydet
        manager.addExpense(new Expense(desc, amount, tryAmount, category, LocalDate.now()));

        redirectAttributes.addFlashAttribute("successMessage", "✅ Expense added successfully!");
        return "redirect:/";
    }
}