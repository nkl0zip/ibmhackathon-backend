from __future__ import annotations

from pathlib import Path
from typing import Dict, List, Any

import numpy as np
import joblib
import shap

# Paths to the serialized model artifacts
_MODEL_DIR = Path(__file__).resolve().parent / "model"
_SCALER_PATH = _MODEL_DIR / "scaler.pkl"
_IFOREST_PATH = _MODEL_DIR / "isolation_forest_model.pkl"

# Load scaler and model once when module is imported
scaler = joblib.load(_SCALER_PATH)
model = joblib.load(_IFOREST_PATH)

# SHAP explainer for feature contribution
explainer = shap.TreeExplainer(model)

def predict(features: Dict[str, float], top_n: int = 3) -> Dict[str, Any]:
    """Run inference on a single feature dictionary.

    Parameters
    ----------
    features: dict
        Mapping of feature name to value.
    top_n: int, default 3
        Number of top contributing features to return.

    Returns
    -------
    dict
        Dictionary containing prediction, risk score and feature contributions.
    """
    # Ensure the features are ordered according to the scaler's expectations
    feature_names = list(getattr(scaler, "feature_names_in_", sorted(features.keys())))
    values = np.array([[features.get(name, 0.0) for name in feature_names]])

    scaled = scaler.transform(values)
    # IsolationForest predicts 1 for inliers, -1 for outliers
    prediction = int(model.predict(scaled)[0])
    # Use negative score_samples as a simple risk score (higher implies higher risk)
    risk_score = float(-model.score_samples(scaled)[0])

    shap_values = explainer.shap_values(scaled)[0]
    contributions = sorted(
        zip(feature_names, shap_values),
        key=lambda item: abs(item[1]),
        reverse=True,
    )
    top_features: List[Dict[str, float]] = [
        {"feature": name, "contribution": float(val)}
        for name, val in contributions[:top_n]
    ]

    return {
        "prediction": prediction,
        "risk_score": risk_score,
        "top_features": top_features,
    }
