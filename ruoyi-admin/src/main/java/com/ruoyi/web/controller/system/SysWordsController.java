package com.ruoyi.web.controller.system;

import java.util.List;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.system.domain.SysWords;
import com.ruoyi.system.service.ISysWordsService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 单词信息Controller
 */
@RestController
@RequestMapping("/system/words")
public class SysWordsController extends BaseController {
    @Autowired
    private ISysWordsService wordsService;

    /**
     * 查询单词列表
     */
    @PreAuthorize("@ss.hasPermi('system:words:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysWords words) {
        startPage();
        List<SysWords> list = wordsService.selectWordsList(words);
        return getDataTable(list);
    }

    /**
     * 导出单词列表
     */
    @PreAuthorize("@ss.hasPermi('system:words:export')")
    @Log(title = "单词", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(SysWords words) {
        List<SysWords> list = wordsService.selectWordsList(words);
        ExcelUtil<SysWords> util = new ExcelUtil<SysWords>(SysWords.class);
        return util.exportExcel(list, "单词数据");
    }

    /**
     * 获取单词详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:words:query')")
    @GetMapping(value = "/{wordId}")
    public AjaxResult getInfo(@PathVariable("wordId") Long wordId) {
        return success(wordsService.selectWordsById(wordId));
    }

    /**
     * 新增单词
     */
    @PreAuthorize("@ss.hasPermi('system:words:add')")
    @Log(title = "单词", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SysWords words) {
        return toAjax(wordsService.insertWords(words));
    }

    /**
     * 修改单词
     */
    @PreAuthorize("@ss.hasPermi('system:words:edit')")
    @Log(title = "单词", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysWords words) {
        return toAjax(wordsService.updateWords(words));
    }

    /**
     * 删除单词
     */
    @PreAuthorize("@ss.hasPermi('system:words:remove')")
    @Log(title = "单词", businessType = BusinessType.DELETE)
    @DeleteMapping("/{wordIds}")
    public AjaxResult remove(@PathVariable Long[] wordIds) {
        return toAjax(wordsService.deleteWordsByIds(wordIds));
    }
}