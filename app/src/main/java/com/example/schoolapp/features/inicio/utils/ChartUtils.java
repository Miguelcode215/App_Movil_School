package com.example.schoolapp.features.inicio.utils;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;

import com.example.schoolapp.features.inicio.model.ConteoAsistencias;
import com.example.schoolapp.features.inicio.model.PorcentajeAsistencias;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ChartUtils {

    public static void configurarBarChart(Context context, BarChart barChart, ConteoAsistencias conteo) {
        List<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(0, conteo.Presente));
        entries.add(new BarEntry(1, conteo.Atrasado));
        entries.add(new BarEntry(2, conteo.Ausente));
        entries.add(new BarEntry(3, conteo.Justificado));

        BarDataSet dataSet = new BarDataSet(entries, "Asistencias");
        dataSet.setColors(
                Color.rgb(76, 175, 80),   // Verde - Presente
                Color.rgb(255, 193, 7),   // Amarillo - Atrasado
                Color.rgb(244, 67, 54),   // Rojo - Ausente
                Color.rgb(33, 150, 243)   // Azul - Justificado
        );
        dataSet.setValueTextSize(12f);

        dataSet.setValueFormatter(new com.github.mikephil.charting.formatter.ValueFormatter() {
            @Override
            public String getBarLabel(BarEntry barEntry) {
                return String.valueOf((int) barEntry.getY()); // sin decimales
            }
        });

        boolean isDarkMode = (context.getResources().getConfiguration().uiMode
                & Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES;
        int textColor = isDarkMode ? Color.WHITE : Color.BLACK;
        dataSet.setValueTextColor(textColor);

        BarData barData = new BarData(dataSet);
        barData.setBarWidth(0.9f);

        barChart.setData(barData);
        barChart.setFitBars(true);

        String[] labels = {"Presente", "Atrasado", "Ausente", "Justificado"};
        XAxis xAxis = barChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f);
        xAxis.setLabelCount(labels.length);
        xAxis.setTextSize(12f);
        xAxis.setTextColor(textColor);

        barChart.getAxisLeft().setGranularity(1f);
        barChart.getAxisLeft().setTextSize(12f);
        barChart.getAxisLeft().setTextColor(textColor);
        barChart.getAxisRight().setEnabled(false);

        barChart.getDescription().setEnabled(false);
        barChart.getLegend().setEnabled(false);
        barChart.setScaleEnabled(false);
        barChart.setPinchZoom(false);
        barChart.setExtraBottomOffset(10f);
        barChart.setBackgroundColor(Color.TRANSPARENT);

        barChart.invalidate();
    }

    public static void configurarPieChart(PieChart pieChart, PorcentajeAsistencias porcentajes) {
        List<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(porcentajes.Presente, porcentajes.Presente > 0 ? formatDecimal(porcentajes.Presente) : ""));
        entries.add(new PieEntry(porcentajes.Atrasado, porcentajes.Atrasado > 0 ? formatDecimal(porcentajes.Atrasado) : ""));
        entries.add(new PieEntry(porcentajes.Ausente, porcentajes.Ausente > 0 ? formatDecimal(porcentajes.Ausente) : ""));
        entries.add(new PieEntry(porcentajes.Justificado, porcentajes.Justificado > 0 ? formatDecimal(porcentajes.Justificado) : ""));

        PieDataSet dataSet = new PieDataSet(entries, "Asistencias");
        dataSet.setColors(
                Color.rgb(76, 175, 80),
                Color.rgb(255, 193, 7),
                Color.rgb(244, 67, 54),
                Color.rgb(33, 150, 243)
        );
        dataSet.setValueTextSize(0f);
        dataSet.setDrawValues(false);

        PieData pieData = new PieData(dataSet);
        pieChart.setData(pieData);

        pieChart.setUsePercentValues(false);
        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleRadius(40f);
        pieChart.setTransparentCircleRadius(45f);
        pieChart.setEntryLabelColor(Color.BLACK);
        pieChart.setEntryLabelTextSize(14f);
        pieChart.getDescription().setEnabled(false);
        pieChart.setDrawEntryLabels(true);
        pieChart.getLegend().setEnabled(false);

        pieChart.invalidate();
    }

    public static String formatDecimal(float value) {
        int decimal = (int)((value * 100) % 100); // obtiene los 2 decimales
        if (decimal == 0) {
            return String.format(Locale.US, "%.0f%%", value); // sin decimales si es 0
        } else {
            return String.format(Locale.US, "%.2f%%", value); // con 2 decimales
        }
    }

}
