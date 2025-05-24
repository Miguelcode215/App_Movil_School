package com.example.schoolapp.features.inicio.adapter;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.schoolapp.R;
import com.example.schoolapp.core.model.Alumno;
import com.example.schoolapp.features.inicio.model.AsistenciaResumen;
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
import java.util.Map;

public class AlumnoResumenAdapter extends RecyclerView.Adapter<AlumnoResumenAdapter.ViewHolder> {

    private final List<Alumno> alumnos;
    private final Map<Integer, AsistenciaResumen> mapaResumen;
    private Context context;


    public AlumnoResumenAdapter(List<Alumno> alumnos, Map<Integer, AsistenciaResumen> mapaResumen) {
        this.alumnos = alumnos;
        this.mapaResumen = mapaResumen;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        this.context = parent.getContext();

        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.inicio_item_alumno, parent, false);
        return new ViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Alumno alumno = alumnos.get(position);
        int idAlumno = alumno.getId_Alumno();

        AsistenciaResumen resumen = mapaResumen.get(idAlumno);

        if (resumen != null) {
            holder.textNombre.setText(alumno.getNombreCompleto());
            holder.textGrado.setText(alumno.getGrados().getNombre_grado());
            holder.textPorcentaje.setText(resumen.getPorcentaje_asistencia() + "%");
            holder.textFaltas.setText(resumen.getConteo_faltas() + " faltas");

            Map<String, Integer> conteo = resumen.getConteo();
            Map<String, Float> distribucion = resumen.getDistribucion_porcentual();

            ConteoAsistencias conteoAsistencias = new ConteoAsistencias(
                    conteo.getOrDefault("Presente", 0),
                    conteo.getOrDefault("Atrasado", 0),
                    conteo.getOrDefault("Ausente", 0),
                    conteo.getOrDefault("Justificado", 0)
            );

            PorcentajeAsistencias porcentajes = new PorcentajeAsistencias(
                    distribucion.getOrDefault("Presente", 0f),
                    distribucion.getOrDefault("Atrasado", 0f),
                    distribucion.getOrDefault("Ausente", 0f),
                    distribucion.getOrDefault("Justificado", 0f)
            );

            configurarBarChart(holder.barChart, conteoAsistencias);
            configurarPieChart(holder.pieChart, porcentajes);
        }
    }

    @Override
    public int getItemCount() {
        return alumnos.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textNombre, textGrado, textPorcentaje, textFaltas;
        BarChart barChart;
        PieChart pieChart;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textNombre = itemView.findViewById(R.id.textNombreCompleto);
            textGrado = itemView.findViewById(R.id.textGrado);
            textPorcentaje = itemView.findViewById(R.id.textAsistenciaPorcentaje);
            textFaltas = itemView.findViewById(R.id.textCantidadFaltas);
            barChart = itemView.findViewById(R.id.barChartAsistencia);
            pieChart = itemView.findViewById(R.id.pieChartAsistencia);
        }
    }
    private void configurarBarChart(BarChart barChart, ConteoAsistencias conteo) {
        List<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(0, conteo.presente));
        entries.add(new BarEntry(1, conteo.atrasado));
        entries.add(new BarEntry(2, conteo.ausente));
        entries.add(new BarEntry(3, conteo.justificado));

        BarDataSet dataSet = new BarDataSet(entries, "Asistencias");
        dataSet.setColors(
                Color.rgb(76, 175, 80),   // Verde - Presente
                Color.rgb(255, 193, 7),   // Amarillo - Atrasado
                Color.rgb(244, 67, 54),   // Rojo - Ausente
                Color.rgb(33, 150, 243)   // Azul - Justificado
        );
        dataSet.setValueTextSize(12f);

        // Detectar modo oscuro
        boolean isDarkMode = (context.getResources().getConfiguration().uiMode
                & Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES;

        int textColor = isDarkMode ? Color.WHITE : Color.BLACK;

        dataSet.setValueTextColor(textColor);

        BarData barData = new BarData(dataSet);
        barData.setBarWidth(0.9f);

        barChart.setData(barData);
        barChart.setFitBars(true);

        // Etiquetas eje X
        String[] labels = {"Presente", "Atrasado", "Ausente", "Justificado"};
        XAxis xAxis = barChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f);
        xAxis.setLabelCount(labels.length);
        xAxis.setTextSize(12f);
        xAxis.setTextColor(textColor);

        // Eje Y izquierdo
        barChart.getAxisLeft().setGranularity(1f);
        barChart.getAxisLeft().setTextSize(12f);
        barChart.getAxisLeft().setTextColor(textColor);

        // Eje Y derecho deshabilitado
        barChart.getAxisRight().setEnabled(false);

        // Otros ajustes
        barChart.getDescription().setEnabled(false);
        barChart.getLegend().setEnabled(false);
        barChart.setScaleEnabled(false);
        barChart.setPinchZoom(false);
        barChart.setExtraBottomOffset(10f);

        // Fondo opcional
        barChart.setBackgroundColor(Color.TRANSPARENT);

        barChart.invalidate(); // refrescar gráfico
    }

    private void configurarPieChart(PieChart pieChart, PorcentajeAsistencias porcentajes) {
        List<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(porcentajes.presente, String.format(Locale.US, "%.0f%%", porcentajes.presente)));
        entries.add(new PieEntry(porcentajes.atrasado, String.format(Locale.US, "%.0f%%", porcentajes.atrasado)));
        entries.add(new PieEntry(porcentajes.ausente, String.format(Locale.US, "%.0f%%", porcentajes.ausente)));
        entries.add(new PieEntry(porcentajes.justificado, String.format(Locale.US, "%.0f%%", porcentajes.justificado)));

        PieDataSet dataSet = new PieDataSet(entries, "Asistencias");
        dataSet.setColors(
                Color.rgb(76, 175, 80),   // Verde
                Color.rgb(255, 193, 7),   // Amarillo
                Color.rgb(244, 67, 54),   // Rojo
                Color.rgb(33, 150, 243)   // Azul
        );
        dataSet.setValueTextSize(0f); // Oculta el valor numérico automático
        dataSet.setDrawValues(false); // Desactiva totalmente los valores automáticos

        PieData pieData = new PieData(dataSet);
        pieChart.setData(pieData);

        pieChart.setUsePercentValues(false); // Ya los estás mostrando tú en la etiqueta
        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleRadius(40f);
        pieChart.setTransparentCircleRadius(45f);
        pieChart.setEntryLabelColor(Color.BLACK);
        pieChart.setEntryLabelTextSize(14f); // Tamaño del "25%", etc.
        pieChart.getDescription().setEnabled(false);
        pieChart.setDrawEntryLabels(true); // Muestra tus etiquetas personalizadas
        pieChart.getLegend().setEnabled(false);
        pieChart.invalidate(); // refrescar gráfico
    }



}
