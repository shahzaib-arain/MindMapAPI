import { useState, useEffect } from 'react';
import { journalService } from '../services/api';

const useJournalEntries = () => {
  const [entries, setEntries] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  const fetchEntries = async () => {
    try {
      setLoading(true);
      const response = await journalService.getEntries();
      setEntries(response.data);
    } catch (err) {
      setError(err.message || 'Failed to fetch journal entries');
    } finally {
      setLoading(false);
    }
  };

  const createEntry = async (entryData) => {
    try {
      await journalService.createEntry(entryData);
      await fetchEntries();
    } catch (err) {
      throw err;
    }
  };

  const updateEntry = async (id, entryData) => {
    try {
      await journalService.updateEntry(id, entryData);
      await fetchEntries();
    } catch (err) {
      throw err;
    }
  };

  const deleteEntry = async (id) => {
    try {
      await journalService.deleteEntry(id);
      setEntries(entries.filter(entry => entry.id !== id));
    } catch (err) {
      throw err;
    }
  };

  useEffect(() => {
    fetchEntries();
  }, []);

  return {
    entries,
    loading,
    error,
    createEntry,
    updateEntry,
    deleteEntry,
    refresh: fetchEntries,
  };
};

export default useJournalEntries;