{{/*
Expand the name of the chart.
*/}}
{{- define "drone-management-system.name" -}}
{{- default .Chart.Name .Values.nameOverride | trunc 63 | trimSuffix "-" }}
{{- end }}

{{/*
Create a default fully qualified app name.
We truncate at 63 chars because some Kubernetes name fields are limited to this (by the DNS naming spec).
If release name contains chart name it will be used as a full name.
*/}}
{{- define "drone-management-system.fullname" -}}
{{- if .Values.fullnameOverride }}
{{- .Values.fullnameOverride | trunc 63 | trimSuffix "-" }}
{{- else }}
{{- $name := default .Chart.Name .Values.nameOverride }}
{{- if contains $name .Release.Name }}
{{- .Release.Name | trunc 63 | trimSuffix "-" }}
{{- else }}
{{- printf "%s-%s" .Release.Name $name | trunc 63 | trimSuffix "-" }}
{{- end }}
{{- end }}
{{- end }}

{{/*
Create chart name and version as used by the chart label.
*/}}
{{- define "drone-management-system.chart" -}}
{{- printf "%s-%s" .Chart.Name .Chart.Version | replace "+" "_" | trunc 63 | trimSuffix "-" }}
{{- end }}

{{/*
Common labels
*/}}
{{- define "drone-management-system.labels" -}}
helm.sh/chart: {{ include "drone-management-system.chart" . }}
{{ include "drone-management-system.selectorLabels" . }}
{{- if .Chart.AppVersion }}
app.kubernetes.io/version: {{ .Chart.AppVersion | quote }}
{{- end }}
app.kubernetes.io/managed-by: {{ .Release.Service }}
{{- end }}

{{/*
Selector labels
*/}}
{{- define "drone-management-system.selectorLabels" -}}
app.kubernetes.io/name: {{ include "drone-management-system.name" . }}
app.kubernetes.io/instance: {{ .Release.Name }}
{{- end }}

{{/*
Create the name of the service account to use
*/}}
{{- define "drone-management-system.serviceAccountName" -}}
{{- if .Values.serviceAccount.create }}
{{- default (include "drone-management-system.fullname" .) .Values.serviceAccount.name }}
{{- else }}
{{- default "default" .Values.serviceAccount.name }}
{{- end }}
{{- end }}

{{- define "fromConfigFile" -}}
{{- $lines := splitList "\n" . -}}
{{- $result := "" -}}
{{- range $line := $lines -}}
  {{- $trimmedLine := trim $line -}}

  {{- if and (ne (len $trimmedLine) 0) (not (hasPrefix  "#" $trimmedLine)) -}}
    {{- $kv := splitList "=" $trimmedLine -}}
    {{- if eq (len $kv) 2 -}}
      {{- $key := trim (index $kv 0) -}}
      {{- $value := trim (index $kv 1) -}}
      {{- $result = printf "%s\n%s: %s" $result $key (quote $value) -}}
    {{- end -}}
  {{- end -}}
{{- end -}}
{{- $result | nindent 2 -}}
{{- end -}}
