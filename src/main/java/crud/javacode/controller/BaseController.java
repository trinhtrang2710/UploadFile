package crud.javacode.controller;

import org.springframework.ui.Model;

public class BaseController {
    private final String START_ITEM_INDEX_KEY = "start";
    private final String LIMIT_KEY = "rowPerPage";
    private final String PRICE_STEP_CONFIG = "priceStepConfig";
    private final String MAX_PAGE_LINE_KEY = "maxPageLine";
    private final String TOTAL_ITEM_KEY = "total";
    private final String TOTAL_PAGE_KEY = "totalPage";
    private final String START_PAGE = "startPage";
    private final String END_PAGE = "endPage";
    protected final String START_TIME = " 00:00:00";
    protected final String END_TIME = " 23:59:59";

    public void setPagingProperty(Model model, int page, int total, int totalPage, int limit) {
        model.addAttribute(START_ITEM_INDEX_KEY, page * limit - limit);
        model.addAttribute(TOTAL_ITEM_KEY, total);
        model.addAttribute(TOTAL_PAGE_KEY, totalPage);
        int maxPage = 5;
        int div = maxPage / 2;
        int start = page - div > 0 ? page - div : 1;
        if (start > 1 && page + div >= totalPage) {
            start = (totalPage - maxPage + 1) > 0 ? (totalPage - maxPage + 1) : 1;
        }
        model.addAttribute(START_PAGE, start);
        model.addAttribute(END_PAGE, start + maxPage - 1 < totalPage ? start + maxPage - 1 : totalPage);
//
    }
    public int pageCalculation(int totalCount, int limit) {
        int totalPage = totalCount / limit;
        if (0 < (totalCount % limit)) {
            ++totalPage;
        }
        return totalPage;
    }

}
