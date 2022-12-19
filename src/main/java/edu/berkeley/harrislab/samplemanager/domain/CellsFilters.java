package edu.berkeley.harrislab.samplemanager.domain;

public class CellsFilters {

    private String cellsId;
    private String organism_name;
    private String cell_line_name;
    private String date_frozen;
    private String QC_test_name;

    private Integer activeSearch;

    public String getCellsId() {
        return cellsId;
    }
    public void setCellsId(String cellsId) {
        this.cellsId = cellsId;
    }

    public String getOrganism_name() {
        return organism_name;
    }
    public void setOrganism_name(String organism_name) {
        this.organism_name = organism_name;
    }

    public String getCell_line_name() {
        return cell_line_name;
    }
    public void setCell_line_name(String cell_line_name) {
        this.cell_line_name = cell_line_name;
    }

    public String getDate_frozen() {
        return date_frozen;
    }
    public void setDate_frozen(String date_frozen) {
        this.date_frozen = date_frozen;
    }

    public String getQC_test_name() {
        return QC_test_name;
    }
    public void setQC_test_name(String QC_test_name) {
        this.QC_test_name = QC_test_name;
    }

    public Integer getActiveSearch() {
        return activeSearch;
    }

    public void setActiveSearch(Integer activeSearch) {
        this.activeSearch = activeSearch;
    }

}
