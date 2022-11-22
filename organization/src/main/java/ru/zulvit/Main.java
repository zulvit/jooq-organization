package ru.zulvit;

import org.jetbrains.annotations.NotNull;
import ru.zulvit.dao.InvoiceDaoImpl;
import ru.zulvit.dao.OrganizationDaoImpl;
import ru.zulvit.dao.OverheadDaoImpl;
import ru.zulvit.dao.ProductDaoImpl;
import ru.zulvit.flyway.FlywayInitializer;
import ru.zulvit.reports.PersonalizedReports;

import java.time.LocalDate;
import java.time.Month;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class Main {
    public static void main(@NotNull String[] args) {
        //init database
        FlywayInitializer.initDb();

        //demonstration dao
        InvoiceDaoImpl invoiceDao = new InvoiceDaoImpl();
        System.out.println(invoiceDao.getAll());
        OrganizationDaoImpl organizationDao = new OrganizationDaoImpl();
        System.out.println(organizationDao.getAll());
        OverheadDaoImpl overheadDao = new OverheadDaoImpl();
        System.out.println(overheadDao.getAll());
        ProductDaoImpl productDao = new ProductDaoImpl();
        System.out.println(productDao.getAll());

        System.out.println("****************");

        LocalDate localDate1 = LocalDate.of(2022, Month.SEPTEMBER, 8);
        LocalDate localDate2 = LocalDate.of(2024, Month.SEPTEMBER, 9);

        //demonstration custom reports
        System.out.println("Выбрать первые N поставщиков по количеству поставленного товара:");
        System.out.println("\t" + new PersonalizedReports().topNByDelivered(3));
        System.out.println("Выбрать поставщиков с количеством поставленного товара выше указанного значения:");
        System.out.println("\t" + new PersonalizedReports().supplierOfAtLeast(20));
        System.out.println("Вывести список товаров, поставленных организациями за период. Если организация товары не поставляла, то она все равно должна быть отражена в списке.");
        System.out.println("\t" + new PersonalizedReports().dateProduct(localDate1, localDate2));
        System.out.println("Рассчитать среднюю цену по каждому товару за период");
        System.out.println("\t" + new PersonalizedReports().calcAveragePriceOfThePeriod(localDate1, localDate2));
        System.out.println("За каждый день для каждого товара рассчитать количество и сумму полученного товара в указанном периоде, посчитать итоги за период");
        System.out.println("\t" + new PersonalizedReports().calcAllProduct(localDate1, localDate2));
    }
}