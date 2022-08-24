$(document).ready(function () {
    for (let i = 0; i < 35; i++) {
        $('#' + 'selectProb' + i).on('change', function () {
            calc()
        });
        $('#' + 'selectImp' + i).on('change', function () {
            calc()
        });
    }
});

function calc() {
    for (let i = 0; i < 35; i++) {
        var probValue = $('#selectProb' + i).val();
        var impValue = $('#selectImp' + i).val();
        let probNum, impNum, result = 0;
        switch (probValue) {
            case 'nie':
                probNum = 0
                break;
            case 'selten':
                probNum = 1
                break;
            case 'mittel':
                probNum = 2
                break;
            case 'haeufig':
                probNum = 3
                break;
            case 'sehr haeufig':
                probNum = 4
                break;
        }
        switch (impValue) {
            case 'keine':
                impNum = 1
                break;
            case 'geringe':
                impNum = 2
                break;
            case 'hohe':
                impNum = 3
                break;
            case 'existenzielle':
                impNum = 4
                break;
        }
        result = probNum * impNum;
        $("#noRisk" + i).hide();
        $("#smallRisk" + i).hide();
        $("#increasedRisk" + i).hide();
        $("#highRisk" + i).hide();
        $("#veryHighRisk" + i).hide();
        switch (true) {
            case (result === 0):
                $("#" + 'noRisk' + i).show()
                break;
            case (result < 5):
                $("#" + 'smallRisk' + i).show()
                break;
            case (result < 10):
                $("#" + 'increasedRisk' + i).show()
                break;
            case (result === 12):
                $("#" + 'highRisk' + i).show()
                break;
            case (result === 16):
                $("#" + 'veryHighRisk' + i).show()
                break;
        }
    }
}

